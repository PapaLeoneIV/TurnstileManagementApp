export class TurnstileInsertDTO{
    available = null;
  
    constructor(init?: Partial<TurnstileInsertDTO>) {
      Object.assign(this, init);
    }
  }
  