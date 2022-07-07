package it.unimib.bank.transfer;

public class DivertRequest {
	private String id;

	public DivertRequest() {
	}

	public DivertRequest(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DivertRequest [id=" + id + "]";
	}
}
