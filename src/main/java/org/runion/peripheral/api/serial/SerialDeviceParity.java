package org.runion.peripheral.api.serial;

/**
 * Parity scheme used for interacting with a serial device.
 * Note: Using verbose naming for clarity in XML
 */
public enum SerialDeviceParity {
    PARITY_NONE,
    PARITY_ODD,
    PARITY_EVEN,
    PARITY_MARK,
    PARITY_SPACE;
}
