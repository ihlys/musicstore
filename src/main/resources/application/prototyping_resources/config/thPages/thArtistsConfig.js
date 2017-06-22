Requests.setRequestObject(new RequestObject("/genres/1/artists"));

var artists = [
	{ 
    id: 1,
		name: "Nightwish",
		imageSmName: "images/sm/Artists/Nightwish-sm.jpg"
	},
	{ 
    id: 2,
		name: "Korn",
		imageSmName: "images/sm/Artists/Korn-sm.jpg"
	},
	{ 
    id: 3,
		name: "System of a down",
		imageSmName: "images/sm/Artists/System_of_a_down-sm.jpg"
	},
	{ 
    id: 4,
		name: "Disturbed",
		imageSmName: "images/sm/Artists/Disturbed-sm.jpg"
	},
	{ 
    id: 6,
		name: "Some artist with long name",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 7,
		name: "Artist7",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 8,
		name: "Artist8",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 9,
		name: "Artist9",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 10,
		name: "Artist10",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 11,
		name: "Artist11",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 12,
		name: "Artist12",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 13,
		name: "Artist13",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 14,
		name: "Artist14",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 15,
		name: "Artist15",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 16,
		name: "Artist16",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 17,
		name: "Artist17",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 18,
		name: "Artist18",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 19,
		name: "Artist19",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 20,
		name: "Artist20",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 21,
		name: "Artist21",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 22,
		name: "Artist22",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 23,
		name: "Artist23",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 24,
		name: "Artist24",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	},
	{ 
    id: 25,
		name: "Artist25",
		imageSmName: "images/sm/Artists/Unknown_artist-sm.jpg"
	}

];

var currentMusicEntity = {
  id: 4,
  name: "Rock",
  imageLgName: "images/lg/Genres/Rock-lg.jpg"
}
//imageLgName: "images/lg/Genres/Classic-lg.jpg"
//imageLgName: "images/lg/Genres/Pop-lg.jpg"
//imageLgName: "images/lg/Genres/HipHop-lg.jpg"
//imageLgName: "images/lg/Genres/RnB-lg.jpg"
//imageLgName: "images/lg/Genres/Rock-lg.jpg";

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
	}
];

var thVars = [

	["artists", artists],
	["musicCollectionFragment", "artists"],
	["currentMusicEntity", currentMusicEntity],
	["newsEvents", newsEvents],
	["navigation", navigation],
  ["initialNextPageUrl", thRequestResponsesPath + "/artists.html"]
	
];
