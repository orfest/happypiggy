package ru.nsu.ccfit.pm.econ.view.shared;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Span;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableViewSelectionListener;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.view.ICompanyMessageListener;
import ru.nsu.ccfit.pm.econ.common.view.ITurnChangeListener;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.IFormatter;

import com.google.inject.Inject;

public class NewsScreen extends GameTab implements Bindable, InjectionVisitable, 
	ICompanyMessageListener, ITurnChangeListener {

	static final Logger logger = LoggerFactory.getLogger(NewsScreen.class);

	@WTKX private Label summaryLabel;
	@WTKX private TableView newsTable;
	@WTKX private PushButton nextPB;
	@WTKX private TextArea descriptionTA;

	/*
	 * Injectables.
	 */
	private IFormatter formatter;
	private CompanyRosterGateway companyRosterGw;
	private GameTimeGateway gameTimeGw;

	private HashMap<Integer, Long> messageByIndex = new HashMap<Integer, Long>();
	private HashSet<Long> readMessages = new HashSet<Long>();

	@Inject
	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	@Inject
	public void setCompanyRosterGateway(CompanyRosterGateway gateway) {
		if (this.companyRosterGw != null) {
			logger.warn("Redefining companyRosterGateway");
			this.companyRosterGw.getCompanyMessageListeners().remove(this);
		}

		this.companyRosterGw = gateway;
		this.companyRosterGw.getCompanyMessageListeners().add(this);
	}
	
	@Inject
	public void setGameTimeGateway(GameTimeGateway gateway){
		if (this.gameTimeGw != null){
			logger.warn("redefining GameTimeGateway");
			this.gameTimeGw.getTurnChangeListeners().remove(this);
		}
		this.gameTimeGw = gateway;
		this.gameTimeGw.getTurnChangeListeners().add(this);
	}

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");

		installListeners();
	}

	private void installListeners() {

		newsTable.getTableViewSelectionListeners().add(new TableViewSelectionListener.Adapter() {

			@Override
			public void selectedRangesChanged(TableView arg0, Sequence<Span> arg1) {
				updateNewsClicked();
			}
		});

		nextPB.getButtonPressListeners().add(new ButtonPressListener() {

			@Override
			public void buttonPressed(Button arg0) {
				nextButtonPressed();
			}
		});

	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}

	private void initScreenControls() {
		newsTable.setTableData(new ArrayList<NewsRow>());
		updateNewsClicked();
	}
	
	private void addCompanyMessage(IUTextOnlyCompanyMessage msg) {
		NewsRow row = new NewsRow(msg);
		addTableRow(row);
		
		updateNewsClicked();
	}
	
	@SuppressWarnings("unchecked")
	private void addTableRow(NewsRow row) {
		row.setOrdinal(newsTable.getTableData().getLength());
		((List<NewsRow>)newsTable.getTableData()).add(row);
	}
	
	protected void updateNewsClicked() {
		descriptionTA.setText("");
		
		try {
			int idx = newsTable.getSelectedIndex();
			NewsRow row = (NewsRow) newsTable.getSelectedRow();
			
			if (idx < 0 || row == null) {
				return;
			}
			
			readMessages.add(row.getMessage().getId());
			setDescription(row.getMessage().getMessage());
			row.setRead(true);
		} finally {
			redrawLabel();
		}
	}

	private void redrawLabel() {
		int allnews = messageByIndex.size();
		int readnews = readMessages.size();
		summaryLabel.setText(formatter.formatNewsSummary(allnews, allnews - readnews));
	}

	private void setDescription(String message) {
		for (String line : message.split("\n")) {
			int insPoint = descriptionTA.getDocument().getCharacterCount() - 1;
			descriptionTA.setSelection(insPoint, 0);
			descriptionTA.insertText(line);
			descriptionTA.insertParagraph();
		}
	}

	protected void nextButtonPressed() {
		for (Entry<Integer, Long> el : messageByIndex.entrySet()) {
			long l = el.getValue();
			if (readMessages.contains(l))
				continue;
			newsTable.setSelectedIndex(el.getKey());
			return;
		}
	}

	@Override
	public void onCompanyMessageReceived(IUTextOnlyCompanyMessage companyMessage) {
		long msgId = companyMessage.getId();
		if (messageByIndex.containsValue(msgId))
			return;
		int cnt = messageByIndex.size();
		messageByIndex.put(cnt, msgId);
		addCompanyMessage(companyMessage);
	}

	@Override
	public void onCumulativeCompanyMessagesUpdate(Collection<? extends IUTextOnlyCompanyMessage> addedMessages) {
		for (IUTextOnlyCompanyMessage msg : addedMessages) {
			onCompanyMessageReceived(msg);
		}
	}

	@Override
	public void onGameStart() {
		logger.info("onGameStart");
		initScreenControls();
	}

	@Override
	public void onTurnNumberChange(int newTurnNumber) {
	}

	@Override
	public void onTurnStateChange(boolean isFinished) {
	}
	
	protected class NewsRow implements Comparable<NewsRow> {
		
		private IUTextOnlyCompanyMessage message;
		private boolean isNew = true;
		private int ordinal = 0;
		
		protected NewsRow(IUTextOnlyCompanyMessage message) {
			this.message = message;
		}
		
		public String getTime() {
			return formatter.formatGameTime(message.getPublishTime());
		}
		
		public String getType() {
			return formatter.formatNewsType(message.getType());
		}
		
		public String getNew() {
			return (isNew ? "*" : " ");
		}
		
		public String getHeader() {
			return message.getTitle();
		}
		
		protected int getOrdinal() {
			return ordinal;
		}

		@Override
		public int compareTo(NewsRow o) {
			return getOrdinal() - o.getOrdinal();
		}
		
		protected void setRead(boolean isRead) {
			this.isNew = !isRead;
		}
		
		protected boolean isRead() {
			return !isNew;
		}
		
		protected void setOrdinal(int ordinal) {
			this.ordinal = ordinal;
		}
		
		protected IUTextOnlyCompanyMessage getMessage() {
			return message;
		}
		
	}

}
