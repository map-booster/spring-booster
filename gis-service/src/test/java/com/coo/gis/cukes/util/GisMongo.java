package com.coo.gis.cukes.util;

import org.junit.rules.ExternalResource;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class GisMongo extends ExternalResource {

	private MongodExecutable mongodExecutable;
	private MongodStarter starter = MongodStarter.getDefaultInstance();
	private String bindIp = "127.0.0.1";
	private int port = 12345;
	private IMongodConfig mongodConfig;
	MongodProcess mongod;

	@Override
	protected void after() {

		if (mongodExecutable != null) {
			mongodExecutable.stop();
		}

	}

	@Override
	protected void before() throws Throwable {

		if(null == mongodExecutable) {

			mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
					.net(new Net(bindIp, port, Network.localhostIsIPv6())).build();

			mongodExecutable = starter.prepare(mongodConfig);

		}

		mongod = mongodExecutable.start();

	}

}
