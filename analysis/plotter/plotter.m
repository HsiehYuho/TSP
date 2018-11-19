% This plotter function is for visualization purposes only and performs no
% calculations. It disregards Euclidian vs Geo coordinates and plots on a
% 2D plane, and does not consider edge weights.

function plotter(cityFile, edgeFile, startInd)
    % city: the name of the .txt file containing coordinates with quotes
    % e.g. 'Atlanta.txt'
    
    % edges: the name of the .txt file containing N edges that you wish to
    % connect, one pair per row separated by a space. direction can be
    % disregarded e.g. 1 2 is the same as 2 1
    % e.g. 'edges_example.txt'
    
    % startInd: starting index of your edges file, either 0 or 1
    
    data = load(cityFile);
    edges = load(edgeFile);
    
    % error handling
    if size(data,2) ~= 3
        fprintf("City file is not correct format, must contain 3 columns of the following format:\n");
        fprintf("1 x1 y1\n");
        fprintf("2 x2 y2\n");
        fprintf("...\n");
        exit();
    elseif size(edges,2) ~= 2
        fprintf("Edges file is not correct format, must contain 2 columns of the following format:\n");
        fprintf("node1 node2\n");
        fprintf("node3 node4\n");
        fprintf("node5 node6\n");
        fprintf("...");
        exit();
    end
    
    data = data(:,2:3); % truncate first column (indices)
    if startInd == 0 % city file starts count at 0, shift edges file by +1
        edges = edges + 1;
    end
    
    % plot all points
    x = data(:,1);
    y = data(:,2);
    plot(x,y, 'ro');
    hold on
    
    a = [1:size(x,1)]';
    b = num2str(a);
    c = cellstr(b);
    dx = 0.1;
    dy = 0.1; % displacement so the text does not overlay the data points
    text(x+dx, y+dy, c);
    
    for ii = 1:size(edges,1) % iterate through each pair
        x1 = data(edges(ii,1), 1); % first node, x coord
        y1 = data(edges(ii,1), 2); % first node, y coord
        x2 = data(edges(ii,2), 1); % second node, x coord
        y2 = data(edges(ii,2), 2); % second node, y coord
        plot([x1 x2], [y1 y2], 'b-');
    end
end
