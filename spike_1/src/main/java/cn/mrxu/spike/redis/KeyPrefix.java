package cn.mrxu.spike.redis;

public interface KeyPrefix {

    public int expireSecond();

    public String getPrefix();
}
