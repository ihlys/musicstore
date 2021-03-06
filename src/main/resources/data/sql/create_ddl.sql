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

CREATE MATERIALIZED VIEW LANGUAGE_HAS_EN
REFRESH FORCE ON COMMIT AS
SELECT Count(id) AS en_count
    FROM Language
    WHERE name = DEFAULT_LANGUAGE();

ALTER TABLE LANGUAGE_HAS_EN
ADD CONSTRAINT CH_LANG_HAS_EN CHECK(en_count = 1);

-- *****************************************************************************
CREATE MATERIALIZED VIEW LOG ON songl10n
WITH SEQUENCE, ROWID
(id, song_id, language_id, name)
INCLUDING NEW VALUES;

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

CREATE MATERIALIZED VIEW LOG ON soundtrackl10n
WITH SEQUENCE, ROWID
(id, soundtrack_id, language_id, name, description)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON thematic_compilationl10n
WITH SEQUENCE, ROWID
(id, thematic_compilation_id, language_id, name, description)
INCLUDING NEW VALUES;

-- *****************************************************************************

CREATE MATERIALIZED VIEW SONGL10N_EXISTS_IN_EN
REFRESH FAST ON COMMIT AS
SELECT l10n.song_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM songl10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.song_id;

ALTER TABLE SONGL10N_EXISTS_IN_EN
ADD CONSTRAINT CH_SONGL10N_EN_EXISTS CHECK(l10n_en_count > 0);

-- ----------

CREATE MATERIALIZED VIEW ALBUML10N_EXISTS_IN_EN
REFRESH FAST ON COMMIT AS
SELECT l10n.album_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM albuml10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.album_id;

ALTER TABLE ALBUML10N_EXISTS_IN_EN
ADD CONSTRAINT CH_ALBUML10N_EN_EXISTS CHECK(l10n_en_count > 0);

-- ----------

CREATE MATERIALIZED VIEW ARTISTL10N_EXISTS_IN_EN
REFRESH FAST ON COMMIT AS
SELECT l10n.artist_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM artistl10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.artist_id;

ALTER TABLE ARTISTL10N_EXISTS_IN_EN
ADD CONSTRAINT CH_ARTISTL10N_EN_EXISTS CHECK(l10n_en_count > 0);

-- ----------

CREATE MATERIALIZED VIEW GENREL10N_EXISTS_IN_EN
REFRESH FAST ON COMMIT AS
SELECT l10n.genre_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM genrel10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.genre_id;

ALTER TABLE GENREL10N_EXISTS_IN_EN
ADD CONSTRAINT CH_GENREL10N_EN_EXISTS CHECK(l10n_en_count > 0);

-- ----------

CREATE MATERIALIZED VIEW SOUNDTRACKL10N_EXISTS_IN_EN
REFRESH FAST ON COMMIT AS
SELECT l10n.soundtrack_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM soundtrackl10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.soundtrack_id;

ALTER TABLE SOUNDTRACKL10N_EXISTS_IN_EN
ADD CONSTRAINT CH_SOUNDTRACKL10N_EN_EXISTS CHECK(l10n_en_count > 0);

-- ----------

CREATE MATERIALIZED VIEW THEMATIC_COMPL10N_EXISTS_IN_EN
REFRESH FAST ON COMMIT AS
SELECT l10n.thematic_compilation_id, Sum(CASE WHEN l.name = DEFAULT_LANGUAGE() THEN 1 ELSE 0 END) AS l10n_en_count
    FROM thematic_compilationl10n l10n
    JOIN language l ON l.id = l10n.language_id
    GROUP BY l10n.thematic_compilation_id;

ALTER TABLE THEMATIC_COMPL10N_EXISTS_IN_EN
ADD CONSTRAINT CH_THEMATIC_COMPL10N_EN_EXISTS CHECK(l10n_en_count > 0);

-- *****************************************************************************
CREATE MATERIALIZED VIEW LOG ON song
WITH SEQUENCE, ROWID
(id, album_id)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON album
WITH SEQUENCE, ROWID
(id, image_sm_name, image_lg_name, release_date, album_type, artist_id)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON artist
WITH SEQUENCE, ROWID
(id, image_sm_name, image_lg_name, genre_id)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON genre
WITH SEQUENCE, ROWID
(id, image_sm_name, image_lg_name, parent_genre_id)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON soundtrack
WITH SEQUENCE, ROWID
(id, image_sm_name, image_lg_name, release_date)
INCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON thematic_compilation
WITH SEQUENCE, ROWID
(id, image_sm_name, image_lg_name)
INCLUDING NEW VALUES;

-- *****************************************************************************

CREATE MATERIALIZED VIEW SONG_HAS_L10N
REFRESH FORCE ON COMMIT AS
SELECT l10n.song_id AS l10n_song_id
    FROM song s, songl10n l10n
    WHERE s.id = l10n.song_id (+);

ALTER TABLE SONG_HAS_L10N
ADD CONSTRAINT CH_SONG_HAS_L10N CHECK(l10n_song_id IS NOT NULL);

-- ----------

CREATE MATERIALIZED VIEW ALBUM_HAS_L10N
REFRESH FORCE ON COMMIT AS
SELECT l10n.album_id AS l10n_album_id
    FROM album a, albuml10n l10n
    WHERE a.id = l10n.album_id (+);

ALTER TABLE ALBUM_HAS_L10N
ADD CONSTRAINT CH_ALBUM_HAS_L10N CHECK(l10n_album_id IS NOT NULL);

-- ----------

CREATE MATERIALIZED VIEW ARTIST_HAS_L10N
REFRESH FORCE ON COMMIT AS
SELECT l10n.artist_id AS l10n_artist_id
    FROM artist a, artistl10n l10n
    WHERE a.id = l10n.artist_id (+);

ALTER TABLE ARTIST_HAS_L10N
ADD CONSTRAINT CH_ARTIST_HAS_L10N CHECK(l10n_artist_id IS NOT NULL);

-- ----------

CREATE MATERIALIZED VIEW GENRE_HAS_L10N
REFRESH FORCE ON COMMIT AS
SELECT l10n.genre_id AS l10n_genre_id
    FROM genre g, genrel10n l10n
    WHERE g.id = l10n.genre_id (+);

ALTER TABLE GENRE_HAS_L10N
ADD CONSTRAINT CH_GENRE_HAS_L10N CHECK(l10n_genre_id IS NOT NULL);

-- ----------

CREATE MATERIALIZED VIEW SOUNDTRACK_HAS_L10N
REFRESH FORCE ON COMMIT AS
SELECT l10n.soundtrack_id AS l10n_soundtrack_id
    FROM soundtrack s, soundtrackl10n l10n
    WHERE s.id = l10n.soundtrack_id (+);

ALTER TABLE SOUNDTRACK_HAS_L10N
ADD CONSTRAINT CH_SOUNDTRACK_HAS_L10N CHECK(l10n_soundtrack_id IS NOT NULL);

-- ----------

CREATE MATERIALIZED VIEW THEMATIC_COMPILATION_HAS_L10N
REFRESH FORCE ON COMMIT AS
SELECT l10n.thematic_compilation_id AS l10n_thematic_compilation_id
    FROM thematic_compilation t, thematic_compilationl10n l10n
    WHERE t.id = l10n.thematic_compilation_id (+);

ALTER TABLE THEMATIC_COMPILATION_HAS_L10N
ADD CONSTRAINT CH_THEMATIC_COMP_HAS_L10N CHECK(l10n_thematic_compilation_id IS NOT NULL);
