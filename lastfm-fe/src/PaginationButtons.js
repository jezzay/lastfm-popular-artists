import React, {Component} from "react";

class PaginationButtons extends Component {

    render() {
        let nextButton;
        if (this.props.includeNextButton) {
            nextButton = <button type="button" onClick={this.props.onNext}
                                 name="next_page"
                                 className="next-page btn btn-primary">Next Page
            </button>;
        }

        let previousButton;
        if (this.props.includePreviousButton) {
            previousButton = <button type="button" onClick={this.props.onPrevious}
                                     name="previous_page"
                                     className="next-page btn btn-secondary">
                Previous Page
            </button>;
        }

        return (<div> {previousButton} {nextButton}</div>);
    }
}

export default PaginationButtons;