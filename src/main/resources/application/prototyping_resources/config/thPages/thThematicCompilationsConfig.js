Requests.setRequestObject(new RequestObject("/thematic-compilations"));

var thematicCompilations = [
	{
    id: 1,
		name: "Sport music",
		imageSmName: "images/sm/ThematicCompilations/SportMusic-sm.jpg"
	},
	{
    id: 2,
		name: "Relax music",
		imageSmName: "images/sm/ThematicCompilations/RelaxMusic-sm.jpg"
	},
	{
    id: 3,
		name: "Holiday music",
		imageSmName: "images/sm/ThematicCompilations/HolidayMusic-sm.jpg"
	},
	{
    id: 4,
		name: "Soul music",
		imageSmName: "images/sm/ThematicCompilations/SoulMusic-sm.jpg"
	},
	{
    id: 5,
		name: "Positive music",
		imageSmName: "images/sm/ThematicCompilations/PositiveMusic-sm.jpg"
	},
	{
    id: 6,
		name: "Space music",
		imageSmName: "images/sm/ThematicCompilations/SpaceMusic-sm.jpg"
	},
	{
    id: 7,
		name: "Thematic Compilation7",
		imageSmName: "images/sm/ThematicCompilations/Unknown_thematic-compilation-sm.jpg"
	},
	{
    id: 8,
		name: "Thematic Compilation8",
		imageSmName: "images/sm/ThematicCompilations/Unknown_thematic-compilation-sm.jpg"
	},
	{
    id: 9,
		name: "Thematic Compilation9",
		imageSmName: "images/sm/ThematicCompilations/Unknown_thematic-compilation-sm.jpg"
	},
	{
    id: 10,
		name: "Thematic Compilation10",
		imageSmName: "images/sm/ThematicCompilations/Unknown_thematic-compilation-sm.jpg"
	},
	{
    id: 11,
		name: "Thematic Compilation11",
		imageSmName: "images/sm/ThematicCompilations/Unknown_thematic-compilation-sm.jpg"
	},
	{
    id: 12,
		name: "Thematic Compilation12",
		imageSmName: "images/sm/ThematicCompilations/Unknown_thematic-compilation-sm.jpg"
	},
	{
    id: 13,
		name: "Thematic Compilation13",
		imageSmName: "images/sm/ThematicCompilations/Unknown_thematic-compilation-sm.jpg"
	},
	{
    id: 14,
		name: "Thematic Compilation14",
		imageSmName: "images/sm/ThematicCompilations/Unknown_thematic-compilation-sm.jpg"
	}
];

var navigation = [
	{
		uri: "/",
		label: "Main"
	},
	{
		uri: "/thematic-compilations",
		label: "Thematic compilations"
	}
];

var thVars = [

	["thematicCompilations", thematicCompilations],
	["musicCollectionFragment", "thematicCompilations"],
	["newsEvents", newsEvents],
	["navigation", navigation],
  ["initialNextPageUrl", thRequestResponsesPath + "/thematic-compilations.html"]
	
];

