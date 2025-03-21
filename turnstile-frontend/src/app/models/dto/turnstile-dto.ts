export class TurnstileDTO{
  id: string;
  available: boolean;


  constructor(id: string, available: boolean) {
    this.id = id;
    this.available = available;
  }
}
