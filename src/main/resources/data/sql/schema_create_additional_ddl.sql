-- *****************************************************************************
CREATE OR REPLACE FUNCTION DEFAULT_LANGUAGE
RETURN VARCHAR2
AS
BEGIN
    RETURN 'en';
END;

-- *****************************************************************************
insert into Language values(1, DEFAULT_LANGUAGE);

CREATE MATERIALIZED VIEW LOG ON language
WITH SEQUENCE, ROWID
(id, name)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LanguageHasEn
REFRESH FORCE ON COMMIT AS
SELECT Count(id) AS en_count
    FROM Language
    WHERE name = DEFAULT_LANGUAGE();

ALTER TABLE LanguageHasEn
ADD CONSTRAINT ch_lang_has_en CHECK(en_count = 1);

-- *****************************************************************************
CREATE MATERIALIZED VIEW LOG ON albuml10n
WITH SEQUENCE, ROWID
(id, album_id, language_id, name, description)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON artistl10n
WITH SEQUENCE, ROWID
(id, artist_id, language_id, name, description)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON genrel10n
WITH SEQUENCE, ROWID
(id, genre_id, language_id, name, description)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON songl10n
WITH SEQUENCE, ROWID
(id, song_id, language_id, name)
INCLUDING NEW VALUES;

-- ----------

CREATE MATERIALIZED VIEW AlbumL10nExistsInEnglish
REFRESH FAST ON COMMIT AS
SELECT l10n.album_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM albuml10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.album_id;

ALTER TABLE AlbumL10nExistsInEnglish
ADD CONSTRAINT ch_albuml10n_en_exists CHECK(l10n_en_count > 0);

-- ----------

CREATE MATERIALIZED VIEW ArtistL10nExistsInEnglish
REFRESH FAST ON COMMIT AS
SELECT l10n.artist_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM artistl10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.artist_id;

ALTER TABLE ArtistL10nExistsInEnglish
ADD CONSTRAINT ch_artistl10n_en_exists CHECK(l10n_en_count > 0);

-- ----------

CREATE MATERIALIZED VIEW GenreL10nExistsInEnglish
REFRESH FAST ON COMMIT AS
SELECT l10n.genre_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM genrel10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.genre_id;

ALTER TABLE GenreL10nExistsInEnglish
ADD CONSTRAINT ch_genrel10n_en_exists CHECK(l10n_en_count > 0);

-- ----------

CREATE MATERIALIZED VIEW SongL10nExistsInEnglish
REFRESH FAST ON COMMIT AS
SELECT l10n.song_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM songl10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.song_id;

ALTER TABLE SongL10nExistsInEnglish
ADD CONSTRAINT ch_songl10n_en_exists CHECK(l10n_en_count > 0);

-- *****************************************************************************
CREATE MATERIALIZED VIEW LOG ON genre
WITH SEQUENCE, ROWID
(id, image_sml_url, image_lg_url, parent_genre_id)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON artist
WITH SEQUENCE, ROWID
(id, image_sml_url, image_lg_url, genre_id)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON album
WITH SEQUENCE, ROWID
(id, image_sml_url, image_lg_url, release_date, album_type, artist_id)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON song
WITH SEQUENCE, ROWID
(id, album_id)
INCLUDING NEW VALUES;

-- ----------

CREATE MATERIALIZED VIEW GenreHasL10n
REFRESH FORCE ON COMMIT AS
SELECT l10n.genre_id AS l10n_genre_id
    FROM genre g, genrel10n l10n
    WHERE g.id = l10n.genre_id (+);

ALTER TABLE GenreHasL10n
ADD CONSTRAINT ch_genre_has_l10n CHECK(l10n_genre_id IS NOT NULL);

-- ----------

CREATE MATERIALIZED VIEW ArtistHasL10n
REFRESH FORCE ON COMMIT AS
SELECT l10n.artist_id AS l10n_artist_id
    FROM artist a, artistl10n l10n
    WHERE a.id = l10n.artist_id (+);

ALTER TABLE ArtistHasL10n
ADD CONSTRAINT ch_artist_has_l10n CHECK(l10n_artist_id IS NOT NULL);

-- ----------

CREATE MATERIALIZED VIEW AlbumHasL10n
REFRESH FORCE ON COMMIT AS
SELECT l10n.album_id AS l10n_album_id
    FROM album a, albuml10n l10n
    WHERE a.id = l10n.album_id (+);

ALTER TABLE AlbumHasL10n
ADD CONSTRAINT ch_album_has_l10n CHECK(l10n_album_id IS NOT NULL);

-- ----------

CREATE MATERIALIZED VIEW SongHasL10n
REFRESH FORCE ON COMMIT AS
SELECT l10n.song_id AS l10n_song_id
    FROM song s, songl10n l10n
    WHERE s.id = l10n.song_id (+);

ALTER TABLE SongHasL10n
ADD CONSTRAINT ch_song_has_l10n CHECK(l10n_song_id IS NOT NULL);