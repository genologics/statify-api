package com.genologics.statsapi.services

import akka.actor.Actor
import javax.crypto.KeyAgreement
import java.security.spec.X509EncodedKeySpec
import java.security.{KeyPairGenerator, KeyFactory}
import javax.crypto.spec.DHParameterSpec
import com.genologics.statsapi.util.HexCodec

/**
 * KeyAgreementService.
 *
 * @author Cameron Fieber <cameron.fieber@genologics.com>
 */

case class KeyAgreementRequest(P : BigInt, G : BigInt, L : Int, pub : String)

class KeyAgreementService extends Actor {
    def receive = {
        case r : KeyAgreementRequest => {
            val spec = new DHParameterSpec(r.P.bigInteger, r.G.bigInteger, r.L)
            val keyGen = KeyPairGenerator.getInstance("DH")
            keyGen.initialize(spec)
            val pair = keyGen.genKeyPair

            val x509KeySpec = new X509EncodedKeySpec(HexCodec(r.pub));
            val keyFact = KeyFactory.getInstance("DH");
            val publicKey = keyFact.generatePublic(x509KeySpec);
            val agreement = KeyAgreement.getInstance("DH")
            agreement.init(pair.getPrivate)
            agreement.doPhase(publicKey, true)
            val secret = agreement.generateSecret("AES")
            self.reply((HexCodec(pair.getPublic.getEncoded), HexCodec(secret.getEncoded)))
        }
    }
}