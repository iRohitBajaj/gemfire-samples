package io.pivotal.cloudcache;

import org.apache.geode.management.membership.ClientMembership;
import org.apache.geode.management.membership.ClientMembershipEvent;
import org.apache.geode.management.membership.ClientMembershipListenerAdapter;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.concurrent.CountDownLatch;

public class ClientServerReadyBeanPostProcessor implements BeanPostProcessor {

	private static final CountDownLatch LATCH = new CountDownLatch(1);

	static {
		ClientMembership.registerClientMembershipListener(
				new ClientMembershipListenerAdapter() {
					@Override
					public void memberJoined(ClientMembershipEvent event) {
						LATCH.countDown();
					}
				}
		);
	}

}
