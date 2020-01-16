const request = require('request').defaults({encoding: null});
const cheerio = require('cheerio');
const fs = require('fs');
const b64 = require('base64-img');
const download = require('image-downloader');

let searchedWord = process.argv[2];
let imageUrl = '';

request(`https://pl.wiktionary.org/wiki/${searchedWord}`, (error, response, html) => {
    if (!error && response.statusCode === 200) {
        const $ = cheerio.load(html);

        const translations = $(`#mw-content-text ul li`);

        let englishTranslation = '';
        let dataToExtract = {
            table: []
        };
        let dataFile = {
            word: searchedWord,
            translatedWord: '',
            base64Image: ''
        };

        translations.each(function (i, elem) {
            if ($(this).text().includes(`angielski`)) {
                englishTranslation = $(this).find('a:first-of-type').text();
            }
        });
        console.log(englishTranslation);
        dataFile.translatedWord = englishTranslation;

        imageUrl = $('.thumbinner img').attr('src');

        const options = {
            url: `https:${imageUrl}`,
            dest: `D:/WEB_APPLICATIONS/scrapping-app/images/${searchedWord}.jpg`,
            extractFilename: false
        };

        download.image(options)
            .then(({ filename, image }) => {
                console.log('Saved to', filename);  // Saved to /path/to/dest/image.jpg
                b64.base64(filename, (error, data) => {
                    if(!error) {
                        console.log(data);
                        dataFile.base64Image = data;
                        dataToExtract.table.push(dataFile);
                        fs.appendFileSync(`D:/WEB_APPLICATIONS/scrapping-app/jsonFile/dataFile.json`, JSON.stringify(dataFile));
                    }
                });
            })
            .catch((err) => console.error(err))
            .then(() => {
               fs.unlink(`D:/WEB_APPLICATIONS/scrapping-app/images/${searchedWord}.jpg`, (err) => {
                   if(err) throw err;
               });
            });
    }
});