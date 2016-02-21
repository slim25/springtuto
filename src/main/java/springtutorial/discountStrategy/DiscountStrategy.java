package springtutorial.discountStrategy;

import java.util.Date;
import java.util.Map;

import springtutorial.model.User;

public interface DiscountStrategy {
	DiscountStrategy.DiscountWithPercentage getDiscountPercentage(User user, Date date);

	final class DiscountWithPercentage implements Map.Entry<String, Float> {
		private final String key;
		private Float value;

		public DiscountWithPercentage(String key, Float value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public Float getValue() {
			return value;
		}

		@Override
		public Float setValue(Float value) {
			Float old = this.value;
			this.value = value;
			return old;
		}
	}
}
