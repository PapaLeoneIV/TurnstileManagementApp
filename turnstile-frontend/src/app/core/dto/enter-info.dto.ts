export  class EnterInfoDTO {
    badgeId: number;
    turnstileId: number;

    constructor(badgeId: number, turnstileId: number) {
        this.badgeId = badgeId;
        this.turnstileId = turnstileId;
    }
}
