package fr.carrefour.delivery.exception;

public enum ErrorCodes {


  CLIENT_NOT_FOUND(1000),
  CLIENT_NOT_VALID(1001),
  CLIENT_ALREADY_IN_USE(1002),

  LIVRAISON_NOT_FOUND(2000),
  LIVRAISON_NOT_VALID(2001),
  LIVRAISON_ALREADY_IN_USE(2002),

  BAD_CREDENTIALS(3000);

  private int code;

  ErrorCodes(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
