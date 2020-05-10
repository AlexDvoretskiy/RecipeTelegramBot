package application.webParsing.parsingConfiguration;


import java.util.HashMap;
import java.util.Map;

public class WebResource {
	private String mainURL;
	private String requestURL;
	private String itemClass;
	private ItemPage itemPage;


	public WebResource() {
		itemPage = new ItemPage();
	}

	public String getMainURL() {
		return mainURL;
	}

	public void setMainURL(String mainURL) {
		this.mainURL = mainURL;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getItemClass() {
		return itemClass;
	}

	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}

	public void setItemPage(ItemPage itemPage) {
		this.itemPage = itemPage;
	}

	public ItemPage getItemPage() {
		return itemPage;
	}


	public class ItemPage {
		private String titleClass;
		private String descriptionClass;
		private String portionAmountClass;
		private String cookingTimeClass;
		private String ingredientsClass;
		private String instructionsClass;
		private String imageClass;
		private String adviceClass;

		public ItemPage() {

		}

		public String getTitleClass() {
			return titleClass;
		}

		public void setTitleClass(String titleClass) {
			this.titleClass = titleClass;
		}

		public String getDescriptionClass() {
			return descriptionClass;
		}

		public void setDescriptionClass(String descriptionClass) {
			this.descriptionClass = descriptionClass;
		}

		public String getPortionAmountClass() {
			return portionAmountClass;
		}

		public void setPortionAmountClass(String portionAmountClass) {
			this.portionAmountClass = portionAmountClass;
		}

		public String getCookingTimeClass() {
			return cookingTimeClass;
		}

		public void setCookingTimeClass(String cookingTimeClass) {
			this.cookingTimeClass = cookingTimeClass;
		}

		public String getIngredientsClass() {
			return ingredientsClass;
		}

		public void setIngredientsClass(String ingredientsClass) {
			this.ingredientsClass = ingredientsClass;
		}

		public String getInstructionsClass() {
			return instructionsClass;
		}

		public void setInstructionsClass(String instructionsClass) {
			this.instructionsClass = instructionsClass;
		}

		public String getAdviceClass() {
			return adviceClass;
		}

		public void setAdviceClass(String adviceClass) {
			this.adviceClass = adviceClass;
		}

		public String getImageClass() {
			return imageClass;
		}

		public void setImageClass(String imageClass) {
			this.imageClass = imageClass;
		}
	}
}
