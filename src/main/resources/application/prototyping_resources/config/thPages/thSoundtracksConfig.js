Requests.setRequestObject(new RequestObject("/soundtracks"));

var soundtracks = [
	{
    id: 1,
		name: "Queen of the damned",
		released: 2002,
		imageSmName: "images/sm/Soundtracks/Movies/Queen_of_the_damned-sm.jpg"
	},
	{
    id: 2,
		name: "Soundtrack2",
		released: "-----",
		imageSmName: "images/sm/Soundtracks/Movies/Unknown_soundtrack-sm.jpg"
	},
	{
    id: 3,
		name: "Soundtrack3",
		released: "-----",
		imageSmName: "images/sm/Soundtracks/Movies/Unknown_soundtrack-sm.jpg"
	},
	{
    id: 4,
		name: "Soundtrack4",
		released: "-----",
		imageSmName: "images/sm/Soundtracks/Movies/Unknown_soundtrack-sm.jpg"
	},
	{
    id: 5,
		name: "Soundtrack5",
		released: "-----",
		imageSmName: "images/sm/Soundtracks/Movies/Unknown_soundtrack-sm.jpg"
	},
	{
    id: 6,
		name: "Soundtrack6",
		released: "-----",
		imageSmName: "images/sm/Soundtracks/Movies/Unknown_soundtrack-sm.jpg"
	},
	{
    id: 7,
		name: "Soundtrack7",
		released: "-----",
		imageSmName: "images/sm/Soundtracks/Movies/Unknown_soundtrack-sm.jpg"
	},
	{
    id: 8,
		name: "Soundtrack8",
		released: "-----",
		imageSmName: "images/sm/Soundtracks/Movies/Unknown_soundtrack-sm.jpg"
	}
];

var navigation = [
	{
		uri: "/",
		label: "Main"
	},
	{
		uri: "/genres",
		label: "Soundtracks"
	}
];

var thVars = [

	["soundtracks", soundtracks],
	["musicCollectionFragment", "soundtracks"],
	["newsEvents", newsEvents],
	["navigation", navigation],
  ["initialNextPageUrl", thRequestResponsesPath + "/soundtracks.html"]
	
];