/* global document */
/* global $ */
/* global thPrototypingMode */
/* eslint-disable import/prefer-default-export */

import 'Styles/soundtracks.css';
import 'Styles/thematic-compilations.css';
import 'Styles/genres.css';
import 'Styles/artists.css';
import 'Styles/albums.css';
import 'Styles/songs.css';
import 'Styles/greetings.css';
import 'Styles/menu.css';
import 'Styles/music-info.css';
import './base-page';
import './jquery.grid-collection';
import './jquery.page-scroller';
import './jquery.items-with-content';
import './jquery.text-fit';
import { fetch } from './ajax';

/**
 * The jQuery plugin namespace.
 * @external "jQuery.fn"
 * @see {@link http://docs.jquery.com/Plugins/Authoring The jQuery Plugin Guide}
 */

/**
 * <p>This is the main module of application which represents all javascript code.
 * Webpack takes this file and generates single application.bundle.js in
 * root/static folder. All pages have only one &ltscript&gt tag which loads this
 * file. When page is loaded by browser if this script was loaded previously than
 * it is picked from browser cache, otherwise it is loaded into memory.
 * <p>This is a single page application. Each view page can be loaded
 * synchronously or asynchronously. When client makes initial request, view page
 * is fully rendered and loaded synchronously. In such case script reads value
 * from it's tag data attribute with name <code>'data-initial-page-init'</code>,
 * which corresponds to name of the page and it's initialization function. Script
 * waits until document is ready and automatically executes this function.
 * Initialization function initializes all widgets, adds custom listeners, adds
 * styles to html elements and so on. For asynchronous page loading, when there is
 * already loaded current page and another is needed, this module exports a
 * {@link module:application.loadPage} function, which can be used in custom user
 * events like button click and so on. When page is loaded asynchronously, only
 * it's "#main__content" part is rendered and this part is substituted into
 * initially loaded page.
 * <p> This design is used because of single page application approach to
 * switching pages in application. Each page has one shared set of elements such
 * as header, footer and so on, and a "#main__content" element which is an actual
 * page. User can switch pages asynchronously without needing of javascript
 * sources update. When next page content is loaded, it's javascript is already
 * loaded in memory and only it's components must be initialized which
 * initialization function does.
 *
 * @module application
 */

function initMusicInfo() {
  const musicInfoDetails = $('div.music-info__details');
  const musicInfoExpandBtn = $('div.music-info__expand-btn-wrapper');

  $('button.music-info__expand-btn').click(function () {
    musicInfoDetails.slideToggle('slow');
    musicInfoExpandBtn.toggleClass('music-info__expand-btn-wrapper--show-after');
    musicInfoExpandBtn.toggleClass('music-info__expand-btn-wrapper--show-before');
  });
}

function initNavigation() {
  $('.navigation').itemsWithContent({
    itemSelector: 'button',
    prototypingMode: thPrototypingMode,
  });
}

function initPageLayout() {
  $('.menu').itemsWithContent({
    itemSelector: 'button',
    prototypingMode: thPrototypingMode,
  });
}

const Init = {};

Init.greetings = function () {
  // greetings page js
};

Init.songs = function () {
  initMusicInfo();
  initNavigation();

  $('.music-collection').pageScroller({ prototypingMode: thPrototypingMode });
};

Init.albums = function () {
  initMusicInfo();
  initNavigation();

  $('.music-collection')
    .gridCollection()
    .pageScroller({ scrollPadding: 75, prototypingMode: thPrototypingMode })
    .itemsWithContent({
      itemSelector: 'div.album',
      prototypingMode: thPrototypingMode,
    });

  $('div.album').textfit({ maxFontPixels: 16, minFontPixels: 14 });
};

Init.artists = function () {
  initMusicInfo();
  initNavigation();

  $('.music-collection')
    .gridCollection()
    .pageScroller({ scrollPadding: 25, prototypingMode: thPrototypingMode })
    .itemsWithContent({
      itemSelector: 'div.artist',
      prototypingMode: thPrototypingMode,
    });

  $('div.artist').textfit({ maxFontPixels: 16, minFontPixels: 14 });
};

Init.genres = function () {
  initMusicInfo();
  initNavigation();

  $('.music-collection')
    .gridCollection()
    .pageScroller({ scrollPadding: 65, prototypingMode: thPrototypingMode })
    .itemsWithContent({
      itemSelector: 'div.genre button[class*="genre-content"], div.genre button[class*="genre-subgenres"]',
      prototypingMode: thPrototypingMode,
    });


  const decoratingLinesMargins = 40; // added as :before and :after pseudo elements
  const decoratingLinesMinSpace = decoratingLinesMargins + 40;
  const genreDivSpanWidth = $('div.genre div[class$="genre__title-wrapper"]').width();

  $('div.genre div[class*="genre__title-wrapper"]').textfit({
    maxFontPixels: 32,
    minFontPixels: 22,
    explicitWidth: genreDivSpanWidth - decoratingLinesMinSpace,
  });
};

Init.soundtracks = function () {
  initMusicInfo();
  initNavigation();

  $('.music-collection')
    .gridCollection()
    .pageScroller({ scrollPadding: 75, prototypingMode: thPrototypingMode })
    .itemsWithContent({
      itemSelector: 'div.soundtrack',
      prototypingMode: thPrototypingMode,
    });

  $('div.artist soundtrack').textfit({ maxFontPixels: 16, minFontPixels: 14 });
};

Init.thematicCompilations = function () {
  initMusicInfo();
  initNavigation();

  $('.music-collection')
    .gridCollection()
    .pageScroller({ scrollPadding: 65, prototypingMode: thPrototypingMode })
    .itemsWithContent({
      itemSelector: 'div.thematic-compilation button',
      prototypingMode: thPrototypingMode,
    });

  $('div.thematic-compilation button').textfit({ maxFontPixels: 30, minFontPixels: 24 });
};

function viewInitializationMappings(url) {
  /* eslint-disable no-multi-spaces */
  if (url.match('^/$'))                     Init.greetings();
  if (url.match('^/genres$'))               Init.genres();
  if (url.match('/subgenres$'))             Init.genres();
  if (url.match('/artists$'))               Init.artists();
  if (url.match('/albums$'))                Init.albums();
  if (url.match('/songs$'))                 Init.songs();
  if (url.match('/soundtracks$'))           Init.soundtracks();
  if (url.match('/thematic-compilations$')) Init.thematicCompilations();
  /* eslint-enable no-multi-spaces */
}

const initialPageInit = document.currentScript.getAttribute('data-initial-page-init');

$(function () {
  if (!thPrototypingMode) {
    initPageLayout();
    Init[initialPageInit]();
  } else {
    setTimeout(function () {
      initPageLayout();
      Init[initialPageInit]();
    }, 0);
  }
});

/**
 * <p>Sends an XMLHttpRequest for loading a page and renders it's content into
 * "#main__content" element, after which executes a corresponding
 * initialization function. It has an internal mapping of url addresses to
 * page names, which are also used as names of initialization functions.
 * <p>Additionally, if loaded content contains element with
 * "<code>music-info</code>" class, that is there is a selected music entity,
 * then it hides element with class "<code>menu</code>", otherwise
 * shows it.
 */

export function loadPage(url, onLoad) {
  const promise = fetch(url, thPrototypingMode);
  promise
    .then((request) => {
      if (request.response.indexOf('music-info') !== -1) {
        $('.menu').hide();
      } else {
        $('.menu').show();
      }
      $('#main__content').replaceWith(request.response);
      if (!thPrototypingMode) {
        viewInitializationMappings(url);
      }
      onLoad();
    })
    .catch(console.log);
}
