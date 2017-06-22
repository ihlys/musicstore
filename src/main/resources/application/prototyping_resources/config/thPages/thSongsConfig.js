Requests.setRequestObject(new RequestObject("/songs"));

var songs = [
	{
    id: 1,
		name: "Stargazers",
		duration: "4:28",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 2,
		name: "Gethsemane",
		duration: "5:22",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 3,
		name: "Devil & the Deep Dark Ocean",
		duration: "4:46",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 4,
		name: "Sacrament of Wilderness",
		duration: "4:12",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 5,
		name: "Passion and the Opera",
		duration: "4:50",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 6,
		name: "Swanheart",
		duration: "4:44",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 7,
		name: "Moondance",
		duration: "3:31",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 8,
		name: "The Riddler",
		duration: "5:16",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 9,
		name: "The Pharao Sails to Orion",
		duration: "6:26",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 10,
		name: "Walkin in the Air",
		duration: "5:31",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 11,
		name: "Sleeping sun",
		duration: "4:05",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 12,
		name: "Nightquest",
		duration: "4:16",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 13,
		name: "Song13",
		duration: "4:16",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 14,
		name: "Song14",
		duration: "4:16",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 15,
		name: "Song15",
		duration: "4:16",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	},
	{
    id: 16,
		name: "Song16",
		duration: "4:16",
		url: "audio/looperman-classic-electric-piano-magic-world.mp3"
	}
];

var currentMusicEntity = { 
  id: 1,
  name: "Nightwish",
  imageLgName: "images/lg/Artists/Nightwish-lg.jpg"
};
//imageLgName: "images/lg/Artists/System_of_a_down-lg.jpg"
//imageLgName: "images/lg/Artists/Korn-lg.jpg"
//imageLgName: "images/lg/Artists/Disturbed-lg.jpg"
//imageLgName: "images/lg/Artists/Nightwish-lg.jpg"

var navigation = [
	{
		uri: "/",
		label: "Main"
	},
	{
		uri: "/genres",
		label: "Genres"
	},
	{
		uri: "/genres/1/artists",
		label: "Rock"
	},
	{
		uri: "/genres/2/artists",
		label: "Metal"
	},
	{
		uri: "/genres/3/artists",
		label: "Symphonic metal"
	},
	{
		uri: "/genres/3/artists/1/albums",
		label: "Nightwish"
	},
	{
		uri: "/genres/3/artists/1/albums/1/songs",
		label: "Wishmaster"
	}
];

var thVars = [

	["songs", songs],
	["musicCollectionFragment", "songs"],
	["currentMusicEntity", currentMusicEntity],
	["newsEvents", newsEvents],
	["navigation", navigation],
  ["initialNextPageUrl", thRequestResponsesPath + "/songs.html"]
	
];

