export  class BadgeDTO {
    allowed_enter_time =  "";
    allowed_exit_time =  "";
    expiry =  "";
    rfid =  "";
    constructor(init?: Partial<BadgeDTO>) {
        Object.assign(this, init);
      }
}
