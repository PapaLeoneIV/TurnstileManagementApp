export class TurnstileDTO{
  id = null;
  available = null;

  constructor(init?: Partial<TurnstileDTO>) {
    Object.assign(this, init);
  }
}
