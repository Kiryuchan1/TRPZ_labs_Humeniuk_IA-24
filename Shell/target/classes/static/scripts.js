let availableActions = [];
async function fetchFiles(path = '\\') {
    const url = path ? `/actions/view?path=${encodeURIComponent(path)}` : '/actions/view';
    try {
        const response = await fetch(url, { method: 'POST' });
        if (response.ok) {
            const files = await response.json();
            renderFileTable(files, path);
        } else {
            showMessage(`Помилка: ${response.status} ${response.statusText}`, 'error');
        }
    } catch (error) {
        showMessage(`Помилка під час запиту: ${error.message}`, 'error');
    }
}

function updateDirectoryPath(path) {
    const directoryInput = document.getElementById('directoryInput');
    const currentPath = directoryInput.value;
    const newPath = currentPath.endsWith('\\') ? `${currentPath}${path}` : `${currentPath}\\${path}`;
    directoryInput.value = newPath + '\\';
    fetchFiles(newPath + '\\');
}

function navigateBack() {
    const directoryInput = document.getElementById('directoryInput');
    let currentPath = directoryInput.value;

    if (currentPath === '\\') {
        return;
    }

    if (currentPath.endsWith('\\')) {
        currentPath = currentPath.slice(0, -1);
    }
    const lastSeparatorIndex = currentPath.lastIndexOf('\\');

    let newPath = '\\';
    if (lastSeparatorIndex !== -1) {
        newPath = currentPath.substring(0, lastSeparatorIndex + 1)
    }

    directoryInput.value = newPath;
    fetchFiles(newPath);
}

async function searchFiles(query) {
    const directoryPath = document.getElementById('directoryInput').value;

    try {
        const response = await fetch(`/actions/search?query=${encodeURIComponent(query)}&path=${encodeURIComponent(directoryPath)}`, {
            method: 'POST'
        });

        if (response.ok) {
            const files = await response.json();
            renderFileTable(files, directoryPath);
        } else {
            showMessage(`Помилка: ${response.status} ${response.statusText}`, 'error');
        }
    } catch (error) {
        showMessage(`Помилка під час пошуку: ${error.message}`, 'error');
    }
}





function showMessage(message, type) {
    const messageContainer = document.getElementById('message');
    messageContainer.textContent = message;
    messageContainer.className = type;
}

document.addEventListener('DOMContentLoaded', async () => {
    const initialPath = '\\';
    document.getElementById('directoryInput').value = initialPath;
    fetchFiles(initialPath);
    await fetchAvailableActions();


    const directoryForm = document.getElementById('directoryForm');
    directoryForm.addEventListener('submit', (event) => {
        event.preventDefault();
        const directoryPath = document.getElementById('directoryInput').value;
        fetchFiles(directoryPath);
    });
});

async function copyFile(fileName) {
    const directoryPath = document.getElementById('directoryInput').value;
    const fullFileName = directoryPath + fileName;
    const newFileName = fullFileName.replace(/(\.[^.\s]+)?$/, '-copy$1');
    const command = `copy ${fullFileName} ${newFileName}`;
    try {
        const response = await fetch('/command-line/execute', {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain' },
            body: command,
        });
        if (response.ok) {
            const result = await response.text();
            showMessage(result, 'success');
            fetchFiles(directoryPath);
        } else {
            showMessage(`Помилка: ${response.status} ${response.statusText}`, 'error');
        }
    } catch (error) {
        showMessage(`Помилка при копіюванні: ${error.message}`, 'error');
    }
}



async function deleteFile(fileName) {
    const directoryPath = document.getElementById('directoryInput').value;
    const fullFileName = directoryPath + fileName;
    const command = `delete ${fullFileName}`;
    try {
        const response = await fetch('/command-line/execute', {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain' },
            body: command,
        });
        if (response.ok) {
            const result = await response.text();
            showMessage(result, 'success');
            fetchFiles(directoryPath);
        } else {
            showMessage(`Помилка: ${response.status} ${response.statusText}`, 'error');
        }
    } catch (error) {
        showMessage(`Помилка при видаленні: ${error.message}`, 'error');
    }
}



function renderFileTable(files, currentPath) {
    const tableContainer = document.getElementById('fileTableContainer');
    const tableBody = document.getElementById('fileTableBody');
    const emptyMessageContainer = document.getElementById('emptyMessageContainer');

    if (files.length > 0) {
        tableContainer.style.display = 'block';
        emptyMessageContainer.style.display = 'none';
        tableBody.innerHTML = '';

        files.forEach(file => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${file}</td>
                <td><button onclick="updateDirectoryPath('${file}')">Перейти</button></td>
                <td><button onclick="copyFile('${file}')">Копіювати</button></td>
                <td><button onclick="deleteFile('${file}')">Видалити</button></td>
                <td><button onclick="showMoveInput(this, '${file}')">Переместить</button></td>
                <td class="move-container" style="display: none;">
                    <input type="text" value="${currentPath}" class="move-input">
                    <button onclick="moveFile('${file}', this)">OK</button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } else {
        tableContainer.style.display = 'none';
        emptyMessageContainer.style.display = 'block';
        emptyMessageContainer.querySelector('p').innerText =
            "Вибрана директорія порожня. Бажаєте повернутися назад?";
    }
}

function showMoveInput(button, fileName) {
    const row = button.parentElement.parentElement;
    const moveContainer = row.querySelector('.move-container');
    moveContainer.style.display = 'table-cell';
}

async function moveFile(fileName, button) {
    const directoryPath = document.getElementById('directoryInput').value;
    const fullFileName = directoryPath + fileName;
    const moveInput = button.previousElementSibling;
    const newFilePath = moveInput.value;

    const command = `move ${fullFileName} ${newFilePath}`;
    try {
        const response = await fetch('/command-line/execute', {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain' },
            body: command,
        });
        if (response.ok) {
            const result = await response.text();
            showMessage(result, 'success');
            fetchFiles(directoryPath);
        } else {
            showMessage(`Помилка: ${response.status} ${response.statusText}`, 'error');
        }
    } catch (error) {
        showMessage(`Помилка при переміщенні: ${error.message}`, 'error');
    }
}



async function switchdrive() {
    const driveInput = document.getElementById('driveInput');
    const driveName = driveInput.value.trim().toUpperCase();

    if (!driveName || driveName.length !== 1) {
        showMessage('Будь ласка, введіть правильну назву диска (одна літера).', 'error');
        return;
    }

    const command = `switch ${driveName}`;
    try {
        const response = await fetch(`/command-line/execute`, {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain' },
            body: command,
        });

        if (response.ok) {
            const result = await response.text();
            showMessage(result, 'success');

            const newPath = `${driveName}:\\`;
            document.getElementById('directoryInput').value = newPath;
            fetchFiles(newPath);
        } else {
            showMessage(`Помилка: ${response.status} ${response.statusText}`, 'error');
        }
    } catch (error) {
        showMessage(`Помилка при переключенні диска: ${error.message}`, 'error');
    }
}


async function fetchAvailableActions() {
    try {
        const response = await fetch('/actions');
        if (response.ok) {
            availableActions = await response.json();
        } else {
            console.error("Failed to fetch available actions", response.status, response.statusText);
            availableActions = ["copy", "move", "delete", "search", "switch", "view"];
        }
    } catch (error) {
        console.error("Failed to fetch available actions", error);
        availableActions = ["copy", "move", "delete", "search", "switch", "view"];
    }
}