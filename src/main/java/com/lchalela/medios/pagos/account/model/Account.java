package com.lchalela.medios.pagos.account.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.lchalela.medios.pagos.account.dto.TransactionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE account SET is_actived = false WHERE id=?")
@Where(clause = "is_actived=true")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal balance;
	private BigDecimal pendingTransaction;
	private BigDecimal pedingAccreditation;
	private String typeAccount;
	private String accountNumber;
	private String cbu;
	private String alias;
	private Long userId;
	@Transient
	private List<TransactionDTO> transactionDTO;
	private Boolean isActived;
	
	@PrePersist
	public void newAccount() {
		isActived = true;
		this.balance = new BigDecimal(0);
		this.pendingTransaction = new BigDecimal(0);
		this.pedingAccreditation = new BigDecimal(0);
	}
}
