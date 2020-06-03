export interface ChangePassword {
  password: string;
  repeatedPassword: string;
  recaptchaToken: string;
}

export interface ForgotPasswordId {
  id: string;
  email: string;
}
