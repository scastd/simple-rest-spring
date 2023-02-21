package com.example.restspringtemplate.router;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class containing all routes for the application.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Routes {
	private static final String V_1 = "/api/v1";
	public static final String ROOT_URL = "/";
	public static final String ALL_ROUTES = "/**";
	public static final String TEST_URL = V_1 + "/test";

	/**
	 * GET routes for the application.
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class GetRoute {
		public static final GetRoute INSTANCE = new GetRoute();

		/**
		 * @return an array containing all GET routes allowed to low tier users.
		 */
		public String[] lowTierRoutes() {
			return new String[] {
			};
		}

		/**
		 * @return an array containing all GET routes allowed to medium tier users.
		 */
		public String[] mediumTierRoutes() {
			return new String[] {
			};
		}

		/**
		 * @return an array containing all GET routes allowed to top tier users.
		 */
		public String[] topTierRoutes() {
			return new String[] {
			};
		}
	}

	/**
	 * POST routes for the application.
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class PostRoute {
		public static final PostRoute INSTANCE = new PostRoute();

		public static final String LOGIN_URL = V_1 + "/login";

		/**
		 * @return an array containing all POST routes allowed to low tier users.
		 */
		public String[] lowTierRoutes() {
			return new String[] {
			};
		}

		/**
		 * @return an array containing all POST routes allowed to medium tier users.
		 */
		public String[] mediumTierRoutes() {
			return new String[] {
			};
		}

		/**
		 * @return an array containing all POST routes allowed to top tier users.
		 */
		public String[] topTierRoutes() {
			return new String[] {
			};
		}
	}

	/**
	 * PUT routes for the application.
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class PutRoute {
		public static final PutRoute INSTANCE = new PutRoute();

		/**
		 * @return an array containing all PUT routes allowed to low tier users.
		 */
		public String[] lowTierRoutes() {
			return new String[] {
			};
		}

		/**
		 * @return an array containing all PUT routes allowed to medium tier users.
		 */
		public String[] mediumTierRoutes() {
			return new String[] {
			};
		}

		/**
		 * @return an array containing all PUT routes allowed to top tier users.
		 */
		public String[] topTierRoutes() {
			return new String[] {
			};
		}
	}

	/**
	 * DELETE routes for the application.
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class DeleteRoute {
		public static final DeleteRoute INSTANCE = new DeleteRoute();

		/**
		 * @return an array containing all DELETE routes allowed to low tier users.
		 */
		public String[] lowTierRoutes() {
			return new String[] {
			};
		}

		/**
		 * @return an array containing all DELETE routes allowed to medium tier users.
		 */
		public String[] mediumTierRoutes() {
			return new String[] {
			};
		}

		/**
		 * @return an array containing all DELETE routes allowed to top tier users.
		 */
		public String[] topTierRoutes() {
			return new String[] {
			};
		}
	}
}
