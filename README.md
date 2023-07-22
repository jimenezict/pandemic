# pandemic

## API REST end point

### Game end point

This end point creates and reads games. All games have a UUID for identifying it which is generated by the back-end.
The UUID will be the identifier for searching games on all the end-points. 
In case of searching for a game the result will come as a GameResponseDTO format, 
which also includes creation and last update timestamps.

#### Create

End Point

GET /game/create/players/{numPlayers}/pandemic/{numPandemics}

Parameters:
* numPlayers: value between 2 and 4
* numPandemics: number of pandemic cards on the player deck, allowed between 0 and 6

Curl example:
```
curl --location --request GET 'localhost:8080/game/create/players/2/pandemic/5' \
--header 'Content-Type: application/json'
```

Positive result:

Returns the gameId

Errors:
* 400 (Bad request): when number of players is not valid
* 400 (Bad request): when number of pandemic cards is not valid


#### Read

End Point

GET /game/read/{gameId}

Parameters:
* gameId: identifier of the game

Curl example:
```
curl --location --request GET 'localhost:8080/game/read/06f5e481-8d2c-4ece-beec-b2b49c4adfb8'
```

Positive result:

Returns a JSON with the state of the game

Errors:
* 404 (Not found): when gameID could not be found as a valid game

### Turn end-point

On this end-point it is provided the information about missing number of actions and available actions for the active player.

Status brings all the information active player needs for taking a decission on the next movement

#### Status

GET /turn/status/{gameId}

Parameters:
* gameId: identifier of the game

Curl example:
```
curl --location --request GET 'localhost:8080/turn/status/812bc794-d6fb-4c9b-b35a-6b32e51d2c77'
```

Positive result:

Returns a JSON with the state of the turn. Contains:
* missingTurns
* active player: available cards, location and name of the player
* available actions

As example:

```
{
    "missingTurns": 4,
    "activePlayer": {
        "listCard": [
            "Type: CITY City: Bangkok",
            "Type: CITY City: Madrid",
            "Type: CITY City: Riyadh",
            "Type: CITY City: Tehran"
        ],
        "location": {
            "hasCenter": true,
            "virus": "BLUE",
            "name": "Atlanta"
        },
        "name": "Researcher"
    },
    "actionList": [
        "FLYDIRECT:You can directly fly to: Bangkok",
        "FLYDIRECT:You can directly fly to: Madrid",
        "FLYDIRECT:You can directly fly to: Riyadh",
        "FLYDIRECT:You can directly fly to: Tehran",
        "DRIVEFERRY:You can drive to: Chicago",
        "DRIVEFERRY:You can drive to: Washington",
        "DRIVEFERRY:You can drive to: Miami"
    ]
}
```

Errors:
* 404 (Not found): when gameID could not be found as a valid game

#### Execute

POST /turn/execute/

body:

{
    "uuid": gameId,
    "playerName": name of the player e.g.: Researcher,
    "actionPosition": integer position counting from 0
}

Curl example:
```
curl --location --request POST 'localhost:8080/turn/execute/' \
--header 'Content-Type: application/json' \
--data-raw '{"uuid":"6dff8fb2-c5ee-45df-b5ea-c8c869900138","playerName":"Researcher","actionPosition":0}'
```
Positive result:

Returns a JSON with the state of the turn. Contains:
* end point
* gameID: 
* message
* player name 
* player location


As example:
```
{
    "endpoint": "Turn End Point",
    "gameID": "6dff8fb2-c5ee-45df-b5ea-c8c869900138",
    "message": "Action executed correctly",
    "playerName": "Researcher",
    "playerLocation": "Montreal"
}
```

Errors:
* 404 (Not found): when gameID could not be found as a valid game
* 404 (Not found): when requester is not the valid player
* 404 (Not found): when requester is not executing the correct action

## Sonar Commands

gradle test jacocoTestReport sonarqube

gradle sonar
