/* global XMLHttpRequest */
/* eslint-disable import/prefer-default-export */

/**
 * Contains methods for sending ajax requests.
 *
 * @module ajax
 */

/**
 * Creates a promise that sends XMLHttpRequest on specified URL address. Also
 * adds <code>X-Requested-With</code> header with value "XMLHttpRequest" to
 * request.
 *
 * @param {string} url  the URL address of request to send
 * @param {boolean} prototypingMode  determines if prototyping mode is enabled.
 *                                   If enabled then request response is always
 *                                   resolved regardles of status code.
 * @return {object} promise that sends XMLHttpRequest
 */

// can be export default, but then JsDoc can't properly document it
// see https://github.com/jsdoc3/jsdoc/issues/1132
export function fetch(url, prototypingMode = false) {
  return new Promise(function (resolve, reject) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

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
