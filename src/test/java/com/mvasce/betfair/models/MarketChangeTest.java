package com.mvasce.betfair.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketChangeTest {
    @Test
    void deserialize() throws JsonProcessingException {
        String json = """
                {
                	"marketChange": {
                		"rc": [
                			{
                				"tv": 0,
                				"batb": null,
                				"spb": null,
                				"bdatl": [
                					[
                						0,
                						0,
                						0
                					],
                					[
                						1,
                						0,
                						0
                					],
                					[
                						2,
                						0,
                						0
                					],
                					[
                						3,
                						0,
                						0
                					],
                					[
                						4,
                						0,
                						0
                					],
                					[
                						5,
                						0,
                						0
                					],
                					[
                						6,
                						0,
                						0
                					],
                					[
                						7,
                						0,
                						0
                					],
                					[
                						8,
                						0,
                						0
                					],
                					[
                						9,
                						0,
                						0
                					]
                				],
                				"trd": null,
                				"spf": null,
                				"ltp": 2,
                				"atb": null,
                				"spl": null,
                				"spn": null,
                				"atl": null,
                				"batl": null,
                				"id": 71092852,
                				"hc": null,
                				"bdatb": [
                					[
                						0,
                						0,
                						0
                					],
                					[
                						1,
                						0,
                						0
                					],
                					[
                						2,
                						0,
                						0
                					],
                					[
                						3,
                						0,
                						0
                					],
                					[
                						4,
                						0,
                						0
                					],
                					[
                						5,
                						0,
                						0
                					],
                					[
                						6,
                						0,
                						0
                					],
                					[
                						7,
                						0,
                						0
                					],
                					[
                						8,
                						0,
                						0
                					],
                					[
                						9,
                						0,
                						0
                					]
                				]
                			},
                			{
                				"tv": 0,
                				"batb": null,
                				"spb": null,
                				"bdatl": [
                					[
                						0,
                						0,
                						0
                					],
                					[
                						1,
                						0,
                						0
                					],
                					[
                						2,
                						0,
                						0
                					],
                					[
                						3,
                						0,
                						0
                					],
                					[
                						4,
                						0,
                						0
                					],
                					[
                						5,
                						0,
                						0
                					],
                					[
                						6,
                						0,
                						0
                					],
                					[
                						7,
                						0,
                						0
                					],
                					[
                						8,
                						0,
                						0
                					],
                					[
                						9,
                						0,
                						0
                					]
                				],
                				"trd": null,
                				"spf": null,
                				"ltp": 2,
                				"atb": null,
                				"spl": null,
                				"spn": null,
                				"atl": null,
                				"batl": null,
                				"id": 71092844,
                				"hc": null,
                				"bdatb": [
                					[
                						0,
                						0,
                						0
                					],
                					[
                						1,
                						0,
                						0
                					],
                					[
                						2,
                						0,
                						0
                					],
                					[
                						3,
                						0,
                						0
                					],
                					[
                						4,
                						0,
                						0
                					],
                					[
                						5,
                						0,
                						0
                					],
                					[
                						6,
                						0,
                						0
                					],
                					[
                						7,
                						0,
                						0
                					],
                					[
                						8,
                						0,
                						0
                					],
                					[
                						9,
                						0,
                						0
                					]
                				]
                			}
                		],
                		"img": true,
                		"tv": 0,
                		"con": null,
                		"marketDefinition": {
                			"venue": null,
                			"raceType": null,
                			"settledTime": 1720457497,
                			"timezone": "GMT",
                			"eachWayDivisor": null,
                			"regulators": [
                				"MR_INT"
                			],
                			"marketType": "MATCH_ODDS",
                			"marketBaseRate": 5,
                			"numberOfWinners": 1,
                			"countryCode": "GB",
                			"lineMaxUnit": null,
                			"inPlay": true,
                			"betDelay": 5,
                			"bspMarket": false,
                			"bettingType": "ODDS",
                			"numberOfActiveRunners": 0,
                			"lineMinUnit": null,
                			"eventId": "33399570",
                			"crossMatching": false,
                			"runnersVoidable": false,
                			"turnInPlayEnabled": true,
                			"priceLadderDefinition": {
                				"type": "CLASSIC"
                			},
                			"keyLineDefinition": null,
                			"suspendTime": 1720443600,
                			"discountAllowed": true,
                			"persistenceEnabled": true,
                			"runners": [
                				{
                					"sortPriority": 1,
                					"removalDate": null,
                					"id": 71092852,
                					"hc": null,
                					"adjustmentFactor": null,
                					"bsp": null,
                					"status": "LOSER"
                				},
                				{
                					"sortPriority": 2,
                					"removalDate": null,
                					"id": 71092844,
                					"hc": null,
                					"adjustmentFactor": null,
                					"bsp": null,
                					"status": "WINNER"
                				}
                			],
                			"version": 6012908947,
                			"eventTypeId": "4",
                			"complete": true,
                			"openDate": 1720443600,
                			"marketTime": 1720443600,
                			"bspReconciled": false,
                			"lineInterval": null,
                			"status": "CLOSED"
                		},
                		"id": "1.230482790"
                	},
                	"arrivalTime": 1720457676190,
                	"publishTime": 1720457676141,
                	"clk": "AAAAAAAA",
                	"initialClk": "I6iTxNQHI/6ntsIHGpf9i9MH",
                	"heartbeatMs": 5000,
                	"conflateMs": 180000,
                	"segmentType": "NONE",
                	"changeType": "SUB_IMAGE"
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        final MarketChangeMessage marketChange = mapper.readValue(json, MarketChangeMessage.class);
        assertEquals(marketChange.marketChange().getId() , "1.230482790");
//        System.out.println(marketChange);
    }
}