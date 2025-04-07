export  class CompanyDTO {
    name = "";
    address = "";
    constructor(init?: Partial<CompanyDTO>) {
        Object.assign(this, init);
      }
}
