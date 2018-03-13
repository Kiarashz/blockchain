#!/usr/bin/env python3

import bitcoin # pip install pybitcoin
import sys



def generate_private_key():
    valid_private_key = False
    while not valid_private_key:
        private_key = bitcoin.random_key()
        decoded_pk = bitcoin.decode_privkey(private_key, 'hex')
        valid_private_key = 0 < decoded_pk < bitcoin.N
    return private_key, decoded_pk


def main(args):
    hex_decoded_pk, decimal_decoded_pk = generate_private_key()
    print("Private key (hex): {0}.".format(hex_decoded_pk))
    print("Private key (decimal): {0}.".format(decimal_decoded_pk))

    wif_encoded_pk = bitcoin.encode_privkey(decimal_decoded_pk, 'wif')
    print("Private key (wif): {0}.".format(wif_encoded_pk))

    compressed_hex_decoded_pk = hex_decoded_pk + '01'
    print("Compressed private key (hex): {0}.".format(compressed_hex_decoded_pk))

if __name__ == '__main__':
    main(sys.argv[1:])
