package br.com.sysdesc.util.classes;

public class ContadorUtil {

	private Long value;

	private Long step;

	public ContadorUtil() {
		this(0L);
	}

	public ContadorUtil(Long value) {
		this(value, 1L);
	}

	public ContadorUtil(Long value, Long step) {
		this.value = value;
		this.step = step;
	}

	public Long next() {
		this.value = this.value + this.step;
		return this.value;
	}

	public Long previows() {
		this.value = this.value - this.step;
		return this.value;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

}
