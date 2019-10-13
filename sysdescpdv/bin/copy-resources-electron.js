/**
 * Copia os arquivos package.json e main-electron.js para o diretório build, pois são necessários para a build do electron.
 */
const fs = require('fs');
const path = require('path');

const files = ['package.json', 'main.js'];
const pathDist = `build`;

files.forEach(file => {
    fs.copyFile(file, `${pathDist}${path.sep}${file}`, (err) => {
        if (err) throw err;
        console.log(`${file} was copied to ${pathDist}`);
    });
})
