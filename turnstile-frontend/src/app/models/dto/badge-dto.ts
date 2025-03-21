export  class BadgeDTO {
    allowed_enter_time: string;
    allowed_exit_time: string;
    expiry: string;
    rfid: string;
    constructor(allowed_enter_time: string, allowed_exit_time: string, expiry: string, rfid: string) {
        this.allowed_enter_time = allowed_enter_time;
        this.allowed_exit_time = allowed_exit_time;
        this.expiry = expiry;
        this.rfid = rfid;
    }
}
