export class JwtResponse {
  accessToken: string;
  email: string;
  id: number;
  tokenType: string;

  constructor(obj?: Partial<JwtResponse>) {
    Object.assign(this, obj);
  }
}
