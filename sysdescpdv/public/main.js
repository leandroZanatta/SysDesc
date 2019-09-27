const {
    app,
    globalShortcut,
    BrowserWindow,
    ipcMain
} = require('electron');
const path = require('path');
const url = require('url');
const fs = require('fs');


app.commandLine.appendSwitch('disable-gpu');
app.disableHardwareAcceleration();

let win;

function createWindow() {
    win = new BrowserWindow({
        width: 825,
        height: 710,
        fullscreen: true
    });

    globalShortcut.register('CommandOrControl+Shift+Alt+@', () => {
        win.webContents.openDevTools();
    });

    globalShortcut.register('CommandOrControl+Shift+Alt+#', () => {
        win.loadURL(
            url.format({
                pathname: path.join(__dirname, 'index.html'),
                protocol: 'file:',
                slashes: true
            })
        );
    });

  win.loadURL(`file://${path.join(__dirname, '../build/index.html')}`)

    win.on('closed', () => {
        win = null;
    });
}

function closeApp() {
    if (process.platform !== 'darwin') {
        app.quit();
    }
}

app.on('ready', createWindow);

app.on('window-all-closed', () => {
    closeApp();
});

app.on('activate', () => {
    if (win === null) {
        createWindow();
    }
});

ipcMain.on('closeApp', () => {
    closeApp();
});


