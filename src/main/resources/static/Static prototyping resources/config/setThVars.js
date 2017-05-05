
var genres = [
	{
		name: "Classic",
		image: "/Static prototyping resources/images/sm/Genres/ClassicMusic-sm.png"
	},
	{
		name: "Pop",
		image: "/Static prototyping resources/images/sm/Genres/PopMusic-sm.png"
	},
	{
		name: "Rap",
		image: "/Static prototyping resources/images/sm/Genres/RapMusic-sm.png"
	},
	{
		name: "Rock",
		image: "/Static prototyping resources/images/sm/Genres/RockMusic-sm.png"
	},
	{
		name: "RnB",
		image: "/Static prototyping resources/images/sm/Genres/RapMusic-sm.png"
	}
];

var artists = [
	{
		name: "Nightwish",
		image: "/Static prototyping resources/images/sm/Artists/Nightwish-sm.png"
	},
	{
		name: "Korn",
		image: "/Static prototyping resources/images/sm/Artists/Korn-sm.png"
	},
	{
		name: "System of a down",
		image: "/Static prototyping resources/images/sm/Artists/System_of_a_down-sm.png"
	},
	{
		name: "Disturbed",
		image: "/Static prototyping resources/images/sm/Artists/Disturbed-sm.png"
	},
	{
		name: "Rise against the machine",
		image: "/Static prototyping resources/images/sm/Artists/Raise_against_the_machine-sm.png"
	}
];

var thematicCollections = [
	{
		name: "Sport music",
		image: "/Static prototyping resources/images/sm/ThematicCollections/SportMusic-sm.png"
	},
	{
		name: "Relax music",
		image: "/Static prototyping resources/images/sm/ThematicCollections/RelaxMusic-sm.png"
	},
	{
		name: "Holiday music",
		image: "/Static prototyping resources/images/sm/ThematicCollections/HolidayMusic-sm.png"
	},
	{
		name: "Soul music",
		image: "/Static prototyping resources/images/sm/ThematicCollections/SoulMusic-sm.png"
	},
	{
		name: "Positive music",
		image: "/Static prototyping resources/images/sm/ThematicCollections/PositiveMusic-sm.png"
	}
];


/*
var currentMusicEntity = genres[3]; // Rock
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Genres/ClassicMusic-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Genres/PopMusic-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Genres/RapMusic-lg.png";
currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Genres/RockMusic-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Genres/RnBMusic-lg.png";
*/

/*
var currentMusicEntity = artists[3]; // Korn
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Artists/Nightwish-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Artists/System_of_a_down-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Artists/Korn-lg.png";
currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Artists/Disturbed-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/Artists/Raise_against_the_machine-lg.png";
*/

var currentMusicEntity = thematicCollections[0]; // Sport
currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/SportMusic-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/System_of_a_down-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/Korn-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/Disturbed-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/Raise_against_the_machine-lg.png";

var thVars = [

	["genres", genres],
	["artists", artists],
	["thematicCollections", thematicCollections],
	["currentMusicEntity", currentMusicEntity],
	
];