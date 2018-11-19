% Converts a vector of consecutive nodes in a tour to a 2xN array of edges
% and writes to a txt file
% Allows for compatibility between TSP algorithm (outputs a tour) and
% plotter function (connects any set of edges, not necessarily a tour)

function tour2edges(tour, city)
    % tour: an Nx1 or 1xN array of nodes in a tour
    % e.g. [14, 7, 13, 4, 10, 19, 5, 8, 12, 16, 2, 1, 0, 6, 11, 17, 9, 15, 3, 18, 14]
    
    % city: name of city in quotes
    % e.g. 'Atlanta'
    
    % get rid of duplicate edge
    if tour(1) == tour(end)
        tour(end) = [];
    end
    
    % make tour a vertical array if horizontal
    if size(tour,1) > size(tour,2)
        tour = tour';
    end
    
    edges = zeros(size(tour,2), 2);
    edges(:,1) = tour;
    edges(1:end-1,2) = tour(2:end);
    edges(end,2) = tour(1);
    
    fname = city + "_edges.txt";
    
    dlmwrite(fname, edges, ' ');
    
end
