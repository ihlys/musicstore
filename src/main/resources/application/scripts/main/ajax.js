/* global XMLHttpRequest */

export default function fetch(url, prototypingMode = false) {
  return new Promise(function (resolve, reject) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);

    xhr.onload = function () {
      if (!prototypingMode) {
        if (this.status === 200) {
          resolve(this);
        } else {
          const error = new Error(this.statusText);
          error.code = this.status;
          reject(error);
        }
      } else {
        resolve(this);
      }
    };

    xhr.onerror = function () {
      reject(new Error('Network Error'));
    };

    xhr.send();
  });
}
