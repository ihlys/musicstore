BEGIN
    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(1, 'HipHop-lg.jpg', 'HipHop-sm.jpg', null);
    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(2, 'Rock-lg.jpg', 'Rock-sm.jpg', null);
    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(3, 'Pop-lg.jpg', 'Pop-sm.jpg', null);
    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(4, 'Classic-lg.jpg', 'Classic-sm.jpg', null);

    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(5, 'PunkRock-lg.jpg', 'PunkRock-sm.jpg', 2);
    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(6, 'Metal-lg.jpg', 'Metal-sm.jpg', 2);
    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(7, 'NuMetal-lg.jpg', 'NuMetal-sm.jpg', 6);
    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(8, 'SymphonicMetal-lg.jpg', 'SymphonicMetal-sm.jpg', 6);
    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(9, 'IndustrialMetal-lg.jpg', 'IndustrialMetal-sm.jpg', 6);
    INSERT INTO GENRE(id, IMAGE_LG_NAME, IMAGE_SM_NAME, PARENT_GENRE_ID) VALUES(10, 'PowerMetal-lg.jpg', 'PowerMetal-sm.jpg', 6);

    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(1, 'This is Hip-hop music description', 'Hip-hop', 1, 1);
    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(2, 'This is Rock music description', 'Rock', 2, 1);
    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(3, 'This is Pop music description', 'Pop', 3, 1);
    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(4, 'This is Classic music description', 'Classic', 4, 1);
    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(5, 'This is Punk rock music description', 'Punk rock', 5, 1);
    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(6, 'This is Metal music description', 'Metal', 6, 1);
    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(7, 'This is Nu metal music description', 'Nu metal', 7, 1);
    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(8, 'This is Symphonic metal music description', 'Symphonic metal', 8, 1);
    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(9, 'This is Industrial metal music description', 'Industrial metal', 9, 1);
    INSERT INTO GENREL10N(ID, DESCRIPTION, NAME, GENRE_ID, LANGUAGE_ID) VALUES(10, 'This is Power metal music description', 'Power metal', 10, 1);

    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(1, 'Disturbed-lg.jpg', 'Disturbed-sm.jpg', 7);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(2, 'Korn-lg.jpg', 'Korn-sm.jpg', 7);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(3, 'System_of_a_down-lg.jpg', 'System_of_a_down-sm.jpg', 7);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(4, 'Nightwish-lg.jpg', 'Nightwish-sm.jpg', 8);

    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(5, 'Antonin_Dvorak-lg.jpg', 'Antonin_Dvorak-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(6, 'Antonio_Vivaldi-lg.jpg', 'Antonio_Vivaldi-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(7, 'Felix_Mendelssohn-lg.jpg', 'Felix_Mendelssohn-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(8, 'George_Frideric_Handel-lg.jpg', 'George_Frideric_Handel-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(9, 'Georges_Bizet-lg.jpg', 'Georges_Bizet-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(10, 'Giuseppe_Verdi-lg.jpg', 'Giuseppe_Verdi-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(11, 'Johann_Sebastian_Bach-lg.jpg', 'Johann_Sebastian_Bach-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(12, 'Johannes_Brahms-lg.jpg', 'Johannes_Brahms-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(13, 'Ludwig_Van_Beethoven-lg.jpg', 'Ludwig_Van_Beethoven-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(14, 'Pyotr_Ilyich_Tchaikovsky-lg.jpg', 'Pyotr_Ilyich_Tchaikovsky-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(15, 'Richard_Wagner-lg.jpg', 'Richard_Wagner-sm.jpg', 4);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(16, 'Wolfgang_Amadeus_Mozart-lg.jpg', 'Wolfgang_Amadeus_Mozart-sm.jpg', 4);

    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(17, 'Rihanna-lg.jpg', 'Rihanna-sm.jpg', 3);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(18, 'Michael_Jackson-lg.jpg', 'Michael_Jackson-sm.jpg', 3);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(19, 'Katy_Perry-lg.jpg', 'Katy_Perry-sm.jpg', 3);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(20, 'Beyonce-lg.jpg', 'Beyonce-sm.jpg', 3);

    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(21, '2Pac-lg.jpg', '2Pac-sm.jpg', 1);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(22, 'Eminem-lg.jpg', 'Eminem-sm.jpg', 1);

    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(23, 'Rammstein-lg.jpg', 'Rammstein-sm.jpg', 9);
    INSERT INTO ARTIST(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, GENRE_ID) VALUES(24, 'Stratovarius-lg.jpg', 'Stratovarius-sm.jpg', 10);

    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(1, 'This is Distrubed band description.', 'Disturbed', 1, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(2, 'This is Korn band description.', 'Korn', 2, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(3, 'This is System of a down band description.', 'System of a down', 3, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(4, 'This is Nightwish band description.', 'Nightwish', 4, 1);

    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(5, 'This is Antonin Dvorak compositor description.', 'Antonin Dvorak', 5, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(6, 'This is Antonio Vivaldi compositor description.', 'Antonio Vivaldi', 6, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(7, 'This is Felix Mendelssohn compositor description.', 'Felix Mendelssohn', 7, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(8, 'This is George Frideric Handel compositor description.', 'George Frideric Handel', 8, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(9, 'This is Georges Bizet compositor description.', 'Georges Bizet', 9, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(10, 'This is Giuseppe Verdi compositor description.', 'Giuseppe Verdi', 10, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(11, 'This is Johann Sebastian Bach compositor description.', 'Johann Sebastian Bach', 11, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(12, 'This is Johannes Brahms compositor description.', 'Johannes Brahms', 12, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(13, 'This is Ludwig Van Beethoven compositor description.', 'Ludwig Van Beethoven', 13, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(14, 'This is Pyotr Ilyich Tchaikovsky compositor description.', 'Pyotr Ilyich Tchaikovsky', 14, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(15, 'This is Richard Wagner compositor description.', 'Richard Wagner', 15, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(16, 'This is Wolfgang Amadeus Mozart compositor description.', 'Antonio Vivaldi', 16, 1);

    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(17, 'This is Rihanna artist description.', 'Rihanna', 17, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(18, 'This is Michael Jackson artist description.', 'Michael Jackson', 18, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(19, 'This is Katy Perry artist description.', 'Katy Perry', 19, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(20, 'This is Beyonce artist description.', 'Beyonce', 20, 1);

    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(21, 'This is 2Pac artist description.', '2Pac', 21, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(22, 'This is Eminem artist description.', 'Eminem', 22, 1);

    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(23, 'This is Rammstein band description.', 'Rammstein', 23, 1);
    INSERT INTO ARTISTL10N(ID, DESCRIPTION, NAME, ARTIST_ID, LANGUAGE_ID) VALUES(24, 'This is Stratovarius band description.', 'Stratovarius', 24, 1);

    INSERT INTO ALBUM(ID, ALBUM_TYPE, IMAGE_LG_NAME, IMAGE_SM_NAME, RELEASE_DATE, ARTIST_ID) VALUES(1, 'STUDIO_ALBUM', 'Angels_falls_first-lg.jpg', 'Angels_fall_first-sm.jpg', to_date('1997', 'YYYY'), 4);
    INSERT INTO ALBUM(ID, ALBUM_TYPE, IMAGE_LG_NAME, IMAGE_SM_NAME, RELEASE_DATE, ARTIST_ID) VALUES(2, 'STUDIO_ALBUM', 'Oceanborn-lg.jpg', 'Oceanborn-sm.jpg', to_date('1998', 'YYYY'), 4);
    INSERT INTO ALBUM(ID, ALBUM_TYPE, IMAGE_LG_NAME, IMAGE_SM_NAME, RELEASE_DATE, ARTIST_ID) VALUES(3, 'STUDIO_ALBUM', 'Wishmaster-lg.jpg', 'Wishmaster-sm.jpg', to_date('2000', 'YYYY'), 4);
    INSERT INTO ALBUM(ID, ALBUM_TYPE, IMAGE_LG_NAME, IMAGE_SM_NAME, RELEASE_DATE, ARTIST_ID) VALUES(4, 'STUDIO_ALBUM', 'Once-lg.jpg', 'Once-sm.jpg', to_date('2004', 'YYYY'), 4);
    INSERT INTO ALBUM(ID, ALBUM_TYPE, IMAGE_LG_NAME, IMAGE_SM_NAME, RELEASE_DATE, ARTIST_ID) VALUES(5, 'STUDIO_ALBUM', 'Dark_passion_play-lg.jpg', 'Dark_passion_play-sm.jpg', to_date('2007', 'YYYY'), 4);
    INSERT INTO ALBUM(ID, ALBUM_TYPE, IMAGE_LG_NAME, IMAGE_SM_NAME, RELEASE_DATE, ARTIST_ID) VALUES(6, 'STUDIO_ALBUM', 'Imaginaerum-lg.jpg', 'Imaginaerum-sm.jpg', to_date('2011', 'YYYY'), 4);
    INSERT INTO ALBUM(ID, ALBUM_TYPE, IMAGE_LG_NAME, IMAGE_SM_NAME, RELEASE_DATE, ARTIST_ID) VALUES(7, 'STUDIO_ALBUM', 'Endless_forms_most_beautiful-lg.jpg', 'Endless_forms_most_beautiful-sm.jpg', to_date('2015', 'YYYY'), 4);

    INSERT INTO ALBUML10N(ID, DESCRIPTION, NAME, ALBUM_ID, LANGUAGE_ID) VALUES(1, 'This is Angels falls first album description.', 'Angels falls first', 1, 1);
    INSERT INTO ALBUML10N(ID, DESCRIPTION, NAME, ALBUM_ID, LANGUAGE_ID) VALUES(2, 'This is Oceanborn album description.', 'Oceanborn', 2, 1);
    INSERT INTO ALBUML10N(ID, DESCRIPTION, NAME, ALBUM_ID, LANGUAGE_ID) VALUES(3, 'This is Wishmaster album description.', 'Wishmaster', 3, 1);
    INSERT INTO ALBUML10N(ID, DESCRIPTION, NAME, ALBUM_ID, LANGUAGE_ID) VALUES(4, 'This is Once album description.', 'Once', 4, 1);
    INSERT INTO ALBUML10N(ID, DESCRIPTION, NAME, ALBUM_ID, LANGUAGE_ID) VALUES(5, 'This is Dark passion play album description.', 'Dark passion play', 5, 1);
    INSERT INTO ALBUML10N(ID, DESCRIPTION, NAME, ALBUM_ID, LANGUAGE_ID) VALUES(6, 'This is Imaginaerum album description.', 'Imaginaerum', 6, 1);
    INSERT INTO ALBUML10N(ID, DESCRIPTION, NAME, ALBUM_ID, LANGUAGE_ID) VALUES(7, 'This is Endless forms most beautiful album description.', 'Endless forms most beautiful', 7, 1);

    -- lately there would be also duration, cost fields etc...
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(1, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(2, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(3, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(4, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(5, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(6, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(7, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(8, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(9, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(10, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(11, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);
    INSERT INTO SONG(ID, URL, ARTIST_ID, ALBUM_ID) VALUES(12, 'looperman-classic-electric-piano-magic-world.mp3', 4, 2);

    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(1, 'Stargazers', 1, 1);
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(2, 'Gethsemane', 2, 1);
    --SET DEFINE OFF;
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(3, 'Devil & the Deep Dark Ocean', 3, 1);
    --SET DEFINE ON;
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(4, 'Sacrament of Wilderness', 4, 1);
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(5, 'Passion and the Opera', 5, 1);
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(6, 'Swanheart', 6, 1);
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(7, 'Moondance', 7, 1);
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(8, 'The Riddler', 8, 1);
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(9, 'The Pharao Sails to Orion', 9, 1);
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(10, 'Walkin in the Air', 10, 1);
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(11, 'Sleeping sun', 11, 1);
    INSERT INTO SONGL10N(ID, NAME, SONG_ID, LANGUAGE_ID) VALUES(12, 'Nightquest', 12, 1);

    INSERT INTO SOUNDTRACK(ID, IMAGE_LG_NAME, IMAGE_SM_NAME, RELEASE_DATE) VALUES(1, 'Queen_of_the_damned-lg.jpg', 'Queen_of_the_damned-sm.jpg', to_date('2002', 'YYYY'));

    INSERT INTO SOUNDTRACKL10N(ID, DESCRIPTION, NAME, SOUNDTRACK_ID, LANGUAGE_ID) VALUES(1, 'This is Queen of the damned soundtrack description.', 'Queen of the damned', 1, 1);

    INSERT INTO THEMATIC_COMPILATION(ID, IMAGE_LG_NAME, IMAGE_SM_NAME) VALUES(1, 'SportMusic-lg.jpg', 'SportMusic-sm.jpg');
    INSERT INTO THEMATIC_COMPILATION(ID, IMAGE_LG_NAME, IMAGE_SM_NAME) VALUES(2, 'PositiveMusic-lg.jpg', 'PositiveMusic-sm.jpg');
    INSERT INTO THEMATIC_COMPILATION(ID, IMAGE_LG_NAME, IMAGE_SM_NAME) VALUES(3, 'HolidayMusic-lg.jpg', 'HolidayMusic-sm.jpg');
    INSERT INTO THEMATIC_COMPILATION(ID, IMAGE_LG_NAME, IMAGE_SM_NAME) VALUES(4, 'RelaxMusic-lg.jpg', 'RelaxMusic-sm.jpg');
    INSERT INTO THEMATIC_COMPILATION(ID, IMAGE_LG_NAME, IMAGE_SM_NAME) VALUES(5, 'SoulMusic-lg.jpg', 'SoulMusic-sm.jpg');
    INSERT INTO THEMATIC_COMPILATION(ID, IMAGE_LG_NAME, IMAGE_SM_NAME) VALUES(6, 'SpaceMusic-lg.jpg', 'SpaceMusic-sm.jpg');

    INSERT INTO THEMATIC_COMPILATIONL10N(ID, DESCRIPTION, NAME, THEMATIC_COMPILATION_ID, LANGUAGE_ID) VALUES(1, 'This is sport music compilation description.', 'Sport', 1, 1);
    INSERT INTO THEMATIC_COMPILATIONL10N(ID, DESCRIPTION, NAME, THEMATIC_COMPILATION_ID, LANGUAGE_ID) VALUES(2, 'This is positive music compilation description.', 'Positive', 2, 1);
    INSERT INTO THEMATIC_COMPILATIONL10N(ID, DESCRIPTION, NAME, THEMATIC_COMPILATION_ID, LANGUAGE_ID) VALUES(3, 'This is holiday music compilation description.', 'Holiday', 3, 1);
    INSERT INTO THEMATIC_COMPILATIONL10N(ID, DESCRIPTION, NAME, THEMATIC_COMPILATION_ID, LANGUAGE_ID) VALUES(4, 'This is relax music compilation description.', 'Relax', 4, 1);
    INSERT INTO THEMATIC_COMPILATIONL10N(ID, DESCRIPTION, NAME, THEMATIC_COMPILATION_ID, LANGUAGE_ID) VALUES(5, 'This is soul music compilation description.', 'Soul', 5, 1);
    INSERT INTO THEMATIC_COMPILATIONL10N(ID, DESCRIPTION, NAME, THEMATIC_COMPILATION_ID, LANGUAGE_ID) VALUES(6, 'This is space music compilation description.', 'Space', 6, 1);

END;
