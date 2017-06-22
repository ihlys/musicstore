Requests.setRequestObject(new RequestObject("/genres"));

var genres = [
	{
    id: 1,
		name: "Classic",
		imageSmName: "images/sm/Genres/Classic-sm.jpg"
	},
	{
    id: 2,
		name: "Pop",
		imageSmName: "images/sm/Genres/Pop-sm.jpg"
	},
	{
    id: 3,
		name: "Rock",
		imageSmName: "images/sm/Genres/Rock-sm.jpg"
	},
	{
    id: 4,
		name: "HipHop",
		imageSmName: "images/sm/Genres/HipHop-sm.jpg"
	},
	{
    id: 5,
		name: "Genre5",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	},
  {
    id: 6,
		name: "Genre6",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	},
	{
    id: 7,
		name: "Genre7",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	},
	{
    id: 8,
		name: "Genre8",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	},
	{
    id: 9,
		name: "Genre9",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	},
	{
    id: 10,
		name: "Genre10",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	},
	{
    id: 11,
		name: "Genre11",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	},
	{
    id: 12,
		name: "Genre12",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	},
	{
    id: 13,
		name: "Genre13",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	}
  ,
	{
    id: 14,
		name: "Genre14",
		imageSmName: "images/sm/Genres/Unknown_genre-sm.jpg"
	}
];


var currentMusicEntity = {
  id: 4,
  name: "HipHop",
  imageLgName: "images/lg/Genres/HipHop-lg.jpg"
}
//imageLgName: "images/lg/Genres/Classic-lg.jpg"
//imageLgName: "images/lg/Genres/Pop-lg.jpg"
//imageLgName: "images/lg/Genres/HipHop-lg.jpg"
//imageLgName: "images/lg/Genres/RockMusic-lg.jpg"

var navigation = [
	{
		uri: "/",
		label: "Main"
	},
	{
		uri: "/genres",
		label: "Genres"
	}
];

var thVars = [

	["genres", genres],
	["musicCollectionFragment", "genres"],
	//["currentMusicEntity", currentMusicEntity],
	["newsEvents", newsEvents],
	["navigation", navigation],
	["initialNextPageUrl", thRequestResponsesPath + "/genres.html"]
	
];
