package ru.nsu.ccfit.pm.econ.view.shared;

import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtkx.Bindable;
import org.apache.pivot.wtkx.WTKX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitor;
import ru.nsu.ccfit.pm.econ.view.shared.localization.IFormatter;

public class CompanyInfoForm extends Form implements Bindable, InjectionVisitable {
	
	static final Logger logger = LoggerFactory.getLogger(CompanyInfoForm.class);
	
	@WTKX private Label companyNameLabel;
	@WTKX private Label companyTypeLabel;
	@WTKX private Label totalSharesLabel;
	@WTKX private Label marketValueLabel;
	@WTKX private Label mineSharesLabel;

	@Override
	public void initialize() {
		logger.debug("instance is bound to wtkx");
	}

	@Override
	public void acceptInjectionVisitor(InjectionVisitor visitor) {
		visitor.injectInto(this);
	}
	
	public void update(IUCompany company, int mineShares, IFormatter formatter){
		companyNameLabel.setText(company != null ? company.getName() : "");
		companyTypeLabel.setText(company != null ? company.getCompanyType() : "");
		totalSharesLabel.setText(company != null ? Integer.toString(company.getTotalSharesAmount()) : "");
		marketValueLabel.setText(company != null ? formatter.formatMoney(company.getShareMarketValue()) : "");
		mineSharesLabel.setText(company != null ? Integer.toString(mineShares) : "");
	}

}
