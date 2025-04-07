export class TurnstileDTO{
  id = 0;
  available = true;

  constructor(init?: Partial<TurnstileDTO>) {
    Object.assign(this, init);
  }
}
