package com.dpi.financial.ftcom.model.to.meb.atm.journal;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.TerminalOperationTypeConverter;
import com.dpi.financial.ftcom.model.converter.TerminalTransactionStateConverter;
import com.dpi.financial.ftcom.model.converter.UserActionConverter;
import com.dpi.financial.ftcom.model.type.terminal.TerminalOperationType;
import com.dpi.financial.ftcom.model.type.terminal.transaction.TerminalTransactionState;
import com.dpi.financial.ftcom.model.type.terminal.transaction.UserAction;

import javax.persistence.*;

/**
 * JournalFile entity persist physical journal file information on database
 *
 * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Monday, December 05, 2016
 * <li>Prepare ATM transactions based on journal content</li>
 */
@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "TERMINAL_OPERATION_STATE_SEQ")
@Table(name = "TERMINAL_OPERATION_STATE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CURRENT_STATE", "OPERATION_TYPE"}),
})
@NamedQueries({
        @NamedQuery(name = TerminalOperationState.FIND_ALL, query = "select t from TerminalOperationState t where t.deleted = false"),
        @NamedQuery(name = TerminalOperationState.FIND_BY_STATE_AND_OPERATION, query = "select t from TerminalOperationState t where t.deleted = false and t.currentState = :currentState and t.operationType = :operationType"),
})
public class TerminalOperationState extends EntityBase {
    public static final String FIND_ALL = "TerminalOperationState.findAll";
    public static final String FIND_BY_STATE_AND_OPERATION = "TerminalOperationState.findByStateAndOperation";

    @Column(name = "CURRENT_STATE", nullable = false, length = 1)
    @Convert(converter = TerminalTransactionStateConverter.class)
    TerminalTransactionState currentState;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 200)
    @Convert(converter = TerminalOperationTypeConverter.class)
    TerminalOperationType operationType;

    @Column(name = "FOLLOWING_STATE", nullable = false, length = 1)
    @Convert(converter = TerminalTransactionStateConverter.class)
    TerminalTransactionState followingState;

    @Column(name = "USER_ACTION", nullable = false, length = 200)
    @Convert(converter = UserActionConverter.class)
    UserAction userAction;

    // todo: put default value for count to 1
    @Column(name = "COUNT", nullable = false)
    Long count;

    public TerminalTransactionState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TerminalTransactionState currentState) {
        this.currentState = currentState;
    }

    public TerminalOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(TerminalOperationType operationType) {
        this.operationType = operationType;
    }

    public TerminalTransactionState getFollowingState() {
        return followingState;
    }

    public void setFollowingState(TerminalTransactionState followingState) {
        this.followingState = followingState;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public UserAction getUserAction() {
        return userAction;
    }

    public void setUserAction(UserAction userAction) {
        this.userAction = userAction;
    }
}