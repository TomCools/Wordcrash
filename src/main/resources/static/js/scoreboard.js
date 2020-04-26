class Scoreboard extends GameObject {
    constructor(startPositionX, startPositionY) {
        super(startPositionX, startPositionY);
        this.playerList = [];
    }

    addPlayerIfNotExists(name) {
        let playerByName = this.findPlayerByName(name);
        if (!playerByName) {
            this.playerList.push(new Player(name));
        }
    }

    addPlayerScore(name, score) {
        let playerByName = this.findPlayerByName(name);
        if (playerByName) {
            playerByName.score += score;
        }
    }

    findPlayerByName(name) {
        for (const player of this.playerList) {
            if (player.name === name) {
                return player;
            }
        }
        return null;
    }

    display() {
        this.drawBaseScoreboard();
        this.drawPlayerScores();
    }

    drawBaseScoreboard() {
        fill(51);
        rect(this.position.x, 0, windowWidth - this.position.x, windowHeight);
        textSize(30);
        fill(0, 250, 250);
        text("SCOREBOARD", this.position.x + 20, 50);
    }

    drawPlayerScores() {
        textSize(20);
        fill(0, 250, 250);
        // Sort on player score
        let distance = 100;
        for (const player of this.playerList.sort(this.sortDecending())) {
            text(player.name + ": " + player.score, this.position.x + 10, this.position.y + distance);
            distance += 40;
        }
    }

    sortDecending() {
        return function (a, b) {
            return b.score - a.score
        };
    }
}

class Player {
    constructor(name) {
        this.name = name;
        this.score = 0;
    }
}