Requests.setRequestObject(new RequestObject("/genres/1/artists/1/albums"));

var albums = [
	{
    id: 1,
		name: "Angels fall first",
		released: 1997,
		imageSmName: "images/sm/Albums/Nightwish/Angels_fall_first-sm.jpg"
	},
	{
    id: 2,
		name: "Ocean born",
		released: 1998,
		imageSmName: "images/sm/Albums/Nightwish/Oceanborn-sm.jpg"
	},	
	{
    id: 3,
		name: "Wishmaster",
		released: 2000,
		imageSmName: "images/sm/Albums/Nightwish/Wishmaster-sm.jpg"
	},
	{
    id: 4,
		name: "Once",
		released: 2004,
		imageSmName: "images/sm/Albums/Nightwish/Once-sm.jpg"
	},
	{
    id: 5,
		name: "Dark Passion Play",
		released: 2007,
		imageSmName: "images/sm/Albums/Nightwish/Dark_Passion_Play-sm.jpg"
	},
	{
    id: 6,
		name: "Imaginaerum",
		released: 2011,
		imageSmName: "images/sm/Albums/Nightwish/Imaginaerum-sm.jpg"
	},
	{
    id: 7,
		name: "Endless forms most beautiful",
		released: 2015,
		imageSmName: "images/sm/Albums/Nightwish/Endless_forms_most_beautiful-sm.jpg"
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
	}
];

var thVars = [

	["albums", albums],
	["musicCollectionFragment", "albums"],
	["currentMusicEntity", currentMusicEntity],
	["newsEvents", newsEvents],
	["navigation", navigation],
  ["initialNextPageUrl", thRequestResponsesPath + "/albums.html"]
	
];
