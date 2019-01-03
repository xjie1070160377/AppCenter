package cn.mooc.app.core.service.cache;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.service.CacheService;

@Service
public class RedisCacheService implements CacheService {

	@Autowired
	private RedisTemplate<String, String> stringTemplate;	
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
		
	@Override
	public <T> T getCache(final String key) {
		final RedisSerializer serializer= redisTemplate.getValueSerializer();
		return redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] datas = connection.get(key.getBytes());
				//System.out.println(serializer.deserialize(datas));
				return (T) serializer.deserialize(datas);
			}
		});	
	}

	@Override
	public boolean setCache(final String key, final Object val) {
		return this.setCache(key, val, 0);
	}

	@Override
	public boolean setCache(final String key, final Object val, final long expire) {
		final RedisSerializer serializer= redisTemplate.getValueSerializer();
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key.getBytes(),serializer.serialize(val));
				
				if (expire > 0) {
					//exp : seconds
                    connection.expire(key.getBytes(), expire);
                }
				return true;
			}
		});
	}
	
	public Long sendTopic(final String topic, final String msg) {
		final RedisSerializer serializer= redisTemplate.getValueSerializer();
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.publish(topic.getBytes(),msg.getBytes());
			}
		});
	}
	
	public Long sendTopic(final String topic, final Object data) {
		final RedisSerializer serializer= redisTemplate.getValueSerializer();
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.publish(topic.getBytes(),serializer.serialize(data));
			}
		});
	}
	
	public boolean exists(final String key) {
    	//检查key是否已经存在
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

	/* 
	 * (non-Javadoc)
	 * @see cn.mooc.app.core.service.CacheService#delCache(java.lang.String)
	 */
	@Override
	public boolean delCache(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				
				long keyCount = connection.del(key.getBytes());
				return keyCount>0;
			}
		});
	}
	
	public Long delCaches(final byte[]... keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				
				long keyCount = connection.del(keys);
				return keyCount;
			}
		});
	}
	
	/**
	 * 支持通配符方式
	 * 例如：delCacheByPattern("sys.user.*")
	 * @param key
	 * @return
	 */
	public boolean delCacheByPattern(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte[]> keyNames = connection.keys(key.getBytes());
				long keyCount = 0;
				for (byte[] keyName : keyNames) {
					keyCount += connection.del(keyName);
				}
				
				return keyCount>0;
			}
		});
	}

	@Override
	public void cleanCache() {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				
				connection.flushDb();
				return true;
			}
		});
	}
	
	public void cleanAllDbCache() {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				
				connection.flushAll();
				return true;
			}
		});
	}
	
	public long dbSize() {
    	//查看redis里有多少数据
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

	public String ping() {
    	//检查是否连接成功
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {

                return connection.ping();
            }
        });
    }
	
}
