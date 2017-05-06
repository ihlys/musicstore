
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
	},
	{
		name: "Space music",
		image: "/Static prototyping resources/images/sm/ThematicCollections/SpaceMusic-sm.png"
	}
];

var albums = [
	{
		name: "Angels fall first",
		released: 1997,
		image: "/Static prototyping resources/images/sm/Albums/Nightwish/Angels_fall_first-sm.png"
	},
	{
		name: "Ocean born",
		released: 1998,
		image: "/Static prototyping resources/images/sm/Albums/Nightwish/Oceanborn-sm.png"
	},	
	{
		name: "Wishmaster",
		released: 2000,
		image: "/Static prototyping resources/images/sm/Albums/Nightwish/Wishmaster-sm.png"
	},
	{
		name: "Once",
		released: 2004,
		image: "/Static prototyping resources/images/sm/Albums/Nightwish/Once-sm.png"
	},
	{
		name: "Dark Passion Play",
		released: 2007,
		image: "/Static prototyping resources/images/sm/Albums/Nightwish/Dark_Passion_Play-sm.png"
	},
	{
		name: "Imaginaerum",
		released: 2011,
		image: "/Static prototyping resources/images/sm/Albums/Nightwish/Imaginaerum-sm.png"
	},
	{
		name: "Endless forms most beautiful",
		released: 2015,
		image: "/Static prototyping resources/images/sm/Albums/Nightwish/Endless_forms_most_beautiful-sm.png"
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
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/RelaxMusic-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/HolidayMusic-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/SoulMusic-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/PositiveMusic-lg.png";
//currentMusicEntity.imageLg = "/Static prototyping resources/images/lg/ThematicCollections/SpaceMusic-lg.png";

var thVars = [

	["genres", genres],
	["artists", artists],
	["albums", albums],
	["thematicCollections", thematicCollections],
	["currentMusicEntity", currentMusicEntity],
	
];