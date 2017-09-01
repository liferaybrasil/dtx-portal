import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import templates from './View.soy';

/**
 * This is the View component.
 */
class View extends Component {
	attached() {
		WeDeploy
		.data(this.dataUrl)
		.orderBy('timestamp', 'asc')
		.limit(1)
		.get('luggage')
		.then((entries) => this.maybeUpdateSteps(entries.pop()));

		WeDeploy
		.data(this.dataUrl)
		.watch('luggage')
		.on('changes', (entries) => this.maybeUpdateSteps(entries.pop()))
		.on('fail', function(error) {
			console.log(error);
		});
	}

	activateStep(step, lastHeardOf) {
		step.check = true;
		if (lastHeardOf) {
			const date = new Date(lastHeardOf.timestamp);
			step.time = date.toLocaleTimeString("en-US");	
		}
		return step;
	}

	deactivateStep(step) {
		step.check = false;
		return step;
	}

	maybeUpdateSteps(lastHeardOf) {
		if (lastHeardOf) {
			let activeIndex = -1;
			this.steps.forEach((step, index) => {
				if (step.label === lastHeardOf.status) {
					activeIndex = index;
				}
			});
			this.steps = this.steps.map((step, index) => {
				if (this.steps[activeIndex].label === 'extraviada') {
					if (this.steps[index] !== 'extraviada') {
						this.activateStep(step);
					}
					else {
						this.deactivateStep(step);	
					}
				}
				else {
					if (index <= activeIndex) {
						this.activateStep(step);
					}
					else {
						this.deactivateStep(step);
					}
				}
				return step;
			});
		}
		else {
			this.steps = this.steps.map(step => this.deactivateStep(step));	
		}
	}
}

View.STATE = {
	dataUrl: {
		value: 'https://db-dtx.wedeploy.io'
	},
	steps: {
		value: []
	}
};

// Register component
Soy.register(View, templates);

export { View };
export default View;