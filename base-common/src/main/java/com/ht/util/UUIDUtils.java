package com.ht.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * 用于对UUID编码生成ID 包含 Base58编码 和 Base64编码
 * @author swt
 */
public class UUIDUtils {

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String base64Uuid() {
        UUID uuid = UUID.randomUUID();
        return base64Uuid(uuid);
    }

    protected static String base64Uuid(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return Base64.encodeBase64URLSafeString(bb.array());
    }

    public static String encodeBase64Uuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return base64Uuid(uuid);
    }

    public static String decodeBase64Uuid(String compressedUuid) {
        byte[] byUuid = Base64.decodeBase64(compressedUuid);

        ByteBuffer bb = ByteBuffer.wrap(byUuid);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }

    public static String base58Uuid() {
        UUID uuid = UUID.randomUUID();
        return base58Uuid(uuid);
    }

    protected static String base58Uuid(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return Base58.encode(bb.array());
    }

    public static String encodeBase58Uuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return base58Uuid(uuid);
    }

    public static String decodeBase58Uuid(String base58uuid) {
        byte[] byUuid = Base58.decode(base58uuid);
        ByteBuffer bb = ByteBuffer.wrap(byUuid);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }
}
