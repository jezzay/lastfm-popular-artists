import React, {Component} from "react";
import Api from "./Api";
class ArtistResult extends Component {

    constructor(props) {
        super(props);
        this.state = {results: [], statusMsg: ''};
        this.fetchArtistTopTracks = this.fetchArtistTopTracks.bind(this);
    }

    fetchArtistTopTracks() {
        Api.artistTopTracks(this.props.mbid).then((res) => {
            this.setState({results: res.data, statusMsg: ''});
        }, (err) => {
            console.error(err.response.then((reason) => {
                console.log(`Failed because of ${reason.error}`);
                this.setState({results: [], statusMsg: reason.error})
            }));
        });
    }

    render() {
        const containerStyle = {
            padding: 10
        };
        let topTracks;
        if (this.state.results.length > 1) {
            topTracks = <div>
                <ul>
                    {this.state.results.map(function (res) {
                        return <li>{res.name}</li>;
                    })}
                </ul>
            </div>;
        }
        return (
            <div className="container" style={containerStyle}>
                <div className="col-xs-1">
                    <img onClick={this.fetchArtistTopTracks} src={this.props.imageURL} alt=""/>
                </div>
                <div className="col-xs-3">
                    {this.props.name}
                </div>
                <div className="col-xs-8">
                    {topTracks}
                </div>
            </div>
        )
    }
}

export default ArtistResult;