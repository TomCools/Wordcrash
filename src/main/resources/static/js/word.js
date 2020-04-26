class Word extends GameObject {
    constructor(startPositionX, text) {
        super(startPositionX, 0);
        this.text = text;
    }

    display() {
        // text(..) is a P5.js method to draw text on the canvas
        textSize(32);
        fill(0, 250, 250);
        text(this.text, this.position.x, this.position.y);
    }

    boxes() {
        // Small bounding box is enough to hit the floor.
        return [new BoundingBox(this.position.x, this.position.y, 1, 1)];
    }
}